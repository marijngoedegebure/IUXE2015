package com.undefined.iuxe2015.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.PlayerStateCallback;
import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.tools.Convert;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class MusicControllerFragment extends MumoFragment {

    public static final String TAG = "MusicControllerFragment";
    @InjectView(R.id.controller_play)
    public ImageButton playButton;
    @InjectView(R.id.controller_pause)
    public ImageButton pauseButton;
    @InjectView(R.id.controller_song_name)
    public TextView songName;
    @InjectView(R.id.controller_song_artists)
    public TextView songArtist;
    @InjectView(R.id.controller_song_progress_text)
    public TextView progressText;
    @InjectView(R.id.controller_progress)
    public ProgressBar progress;

    @Optional
    @InjectView(R.id.controller_btn_rate)
    Button rate;

    @InjectView(R.id.controller_no_song)
    public View noSong;
    @InjectView(R.id.controller_songInfo)
    public View content;

    ProgressSyncer syncer;

    public MusicControllerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music_controller, container, false);
        ButterKnife.inject(this, rootView);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MumoApplication.mPlayer == null) {
                    toast("No player to pause");
                } else {
                    MumoApplication.mPlayer.pause();
                    setPlayPause(false);
                }
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MumoApplication.mPlayer == null) {
                    toast("No player to start");
                } else {
                    MumoApplication.mPlayer.resume();
                    setPlayPause(true);
                }
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MumoActivity) getActivity()).showRatingDialog(MumoApplication.currentlyPlayedSong);
            }
        });
        if (MumoApplication.currentlyPlayedSong != null) {
            refresh();
        } else {
            //TODO go to  'No song playing UI's
            setPlayPause(false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (syncer != null)
            syncer.cancel(true);
    }

    public void refresh() {
        if (syncer != null)
            syncer.cancel(false);
        syncer = new ProgressSyncer();
        syncer.execute();
        setSongTexts();
        setPlayPause(true);
        MumoApplication.mPlayer.getPlayerState(new PlayerStateCallback() {
            @Override
            public void onPlayerState(PlayerState playerState) {
                if (!playerState.playing)
                    if (MumoApplication.currentlyPlayedSong != null) {
                        setPlayPause(true);
                        return;
                    }
                setPlayPause(playerState.playing);
            }
        });
    }

    private void setSongTexts() {
        Song s = MumoApplication.currentlyPlayedSong;
        Player p = MumoApplication.mPlayer;
        if (s != null && p != null) {
            songName.setText(s.getName());
            songArtist.setText(s.getArtistsString(getActivity()));
            progress.setMax((int) s.getDurationMs());
            noSong.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        } else {
            noSong.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        }
    }

    private void setPlayPause(boolean isPlaying) {
        pauseButton.setVisibility(isPlaying ? View.VISIBLE : View.GONE);
        playButton.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
    }

    private class ProgressSyncer extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while (!isCancelled()) {
                MumoApplication.mPlayer.getPlayerState(new PlayerStateCallback() {
                    @Override
                    public void onPlayerState(PlayerState playerState) {
                        publishProgress(playerState.positionInMs);
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (progress != null) {
                progress.setProgress(values[0]);
                progressText.setText(Convert.durationToString(progress.getProgress()) + " / " + Convert.durationToString(progress.getMax()));
            }
        }
    }
}
