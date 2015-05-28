package com.undefined.iuxe2015.fragments;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.adapters.SongSearchAdapter;
import com.undefined.iuxe2015.model.Artist;
import com.undefined.iuxe2015.model.QueryResult;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.tools.ConnectionTool;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by marijngoedegebure on 23-04-15.
 */
public class SongDetailFragment extends MumoFragment {

    public static final String TAG = "SongDetailFragment";

    @InjectView(R.id.song_detail_name)
    TextView name;
    @InjectView(R.id.song_detail_album)
    TextView album;
    @InjectView(R.id.song_detail_artist)
    TextView artist;
    @InjectView(R.id.song_detail_rating)
    ImageView rating;
    @InjectView(R.id.song_detail_rating_text)
    TextView ratingText;
    @InjectView(R.id.song_detail_play_btn)
    Button playSong;

    Song song;
    Rating r;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String songId = getArguments().getString(SongDetailActivity.EXTRA_SONGID);
        if (songId == null || songId.length() == 0) {
            Log.e(TAG, "NO SONGID!!!!!!");
            getActivity().finish();
        } else if (song == null) {
            Log.e(TAG, "NO SONG FOR ID " + songId + " !!!!!!");

            ConnectionTool.getSong(getActivity(), songId, new ConnectionTool.ConnectionSongListener() {
                @Override
                public void onConnectionSuccess(Song result) {
                    //TODO stop loading UI, if visible
                    if (result != null) {
                        Log.d("SongDetailFragment", "onConnectionSuccess:" + result);
                        song = result;
                        Song tempSong = getData().getSongById(getActivity(), song.getId());
                        if(tempSong != null) {
                            r = getData().getRatingsForSong(getActivity(), tempSong);
                        }
                        setDetails();
                    } else {
                        Log.d("SongDetailFragment", "onConnectionSuccess: but no results");
                    }
                }

                @Override
                public void onConnectionFailed(Exception e) {
                    //TODO give ui feedback about what went wrong
                    Log.d("SongDetailFragment", "onConnectionFailed");
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song_detail, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDetails();
    }

    public void refresh() {
        Song tempSong = getData().getSongById(getActivity(), song.getId());
        r = getData().getRatingsForSong(getActivity(), tempSong);
        setDetails();
    }

    private void setDetails() {
        if (song != null) {
            if(r != null) {
                int image;
                switch (r.getRating()) {
                    case 1:
                        image = R.drawable.ic_likert1; break;
                    case 2:
                        image = R.drawable.ic_likert2; break;
                    case 3:
                        image = R.drawable.ic_likert3; break;
                    case 4:
                        image = R.drawable.ic_likert4; break;
                    case 5:
                        image = R.drawable.ic_likert5; break;
                    case 6:
                        image = R.drawable.ic_likert6; break;
                    case 7:
                        image = R.drawable.ic_likert7; break;
                    default:
                        image = R.drawable.ic_likert4;
                }
                rating.setImageResource(image);
                ratingText.setText("");
            }
            else {
                ratingText.setText(R.string.song_detail_song_rating_default_value);
            }
            name.setText(song.getName());
            album.setText(song.getAlbum().getName());
            if (song.getArtists().size() > 0) {
                String artistText = song.getArtists().get(0).getName();
                for (int i = 1; i < song.getArtists().size(); i++) {
                    artistText += ", " + song.getArtists().get(i).getName();
                }
                artist.setText(artistText);
            } else {
                artist.setText("No artists found");
            }

            playSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MumoApplication.mPlayer == null) {
                        toast("No player to start");
                    } else {
                        MumoApplication.mPlayer.play(song.getUri());
                        if(MumoApplication.currentlyPlayedSong == null) {
                            MumoApplication.previouslyPlayedSong = null;
                            MumoApplication.currentlyPlayedSong = song;
                        }
                        else {
                            MumoApplication.previouslyPlayedSong = MumoApplication.currentlyPlayedSong;
                            MumoApplication.currentlyPlayedSong = song;
                        }
                        ((MusicControllerFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.music_controller)).refresh();
                    }
                }
            });
        }
    }
}
