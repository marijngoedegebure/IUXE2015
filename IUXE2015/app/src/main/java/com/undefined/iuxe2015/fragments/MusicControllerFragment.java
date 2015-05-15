package com.undefined.iuxe2015.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spotify.sdk.android.player.Player;
import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.model.Song;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
    @InjectView(R.id.controller_progress)
    public ProgressBar progress;

    public MusicControllerFragment() {
        // Required empty public constructor
    }

    public void startMusic() {
        setSongTexts();
        setPlayPause(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music_controller, container, false);
        ButterKnife.inject(this, rootView);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupActivity.mPlayer.pause();
                setPlayPause(false);
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupActivity.mPlayer.resume();
                setPlayPause(true);
            }
        });
        if (MumoApplication.currentlyPlayedSong != null) {
            setSongTexts();
            setPlayPause(true);
        } else {
            //TODO go to  'No song playing UI'
            setPlayPause(false);
        }
        return rootView;
    }

    private void setSongTexts() {
        Song s = MumoApplication.currentlyPlayedSong;
        if (s != null) {
            songName.setText(s.getName());
            songArtist.setText(s.getArtistsString(getActivity()));
            progress.setMax((int) s.getDurationMs());
        }
    }

    private void setPlayPause(boolean isPlaying) {
        pauseButton.setVisibility(isPlaying ? View.VISIBLE : View.GONE);
        playButton.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
//        playButton.setEnabled(!isPlaying);
//        pauseButton.setEnabled(isPlaying);
    }
}
