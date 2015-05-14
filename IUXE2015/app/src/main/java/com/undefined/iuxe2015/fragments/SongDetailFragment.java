package com.undefined.iuxe2015.fragments;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.adapters.SongSearchAdapter;
import com.undefined.iuxe2015.model.Artist;
import com.undefined.iuxe2015.model.QueryResult;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.tools.ConnectionTool;

/**
 * Created by marijngoedegebure on 23-04-15.
 */
public class SongDetailFragment extends MumoFragment {

    public static final String TAG = "SongDetailFragment";
    Song song;

    TextView name;
    TextView album;
    TextView artist;
    Button playSong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String songId = getArguments().getString(SongDetailActivity.EXTRA_SONGID);
        if(songId == ""){
            Log.e("SONGDETAILFRAGMENT", "NO SONGID!!!!!!");
            getActivity().finish();
        }else{

            song = getData().getSongById(getActivity(), songId);
            if(song == null){
                Log.e("SONGDETAILFRAGMENT", "NO SONG FOR ID " + songId + " !!!!!!");

                ConnectionTool.getSong(getActivity(), songId, new ConnectionTool.ConnectionSongListener() {
                    @Override
                    public void onConnectionSuccess(Song result) {
                        //TODO stop loading UI, if visible
                        if (result != null) {
                            Log.d("SongDetailFragment", "onConnectionSuccess:" + result);
                            song = result;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song_detail, container, false);

        name = (TextView) rootView.findViewById(R.id.song_detail_name);
        album = (TextView) rootView.findViewById(R.id.song_detail_album);
        artist = (TextView) rootView.findViewById(R.id.song_detail_artist);
        playSong = (Button) rootView.findViewById(R.id.song_detail_play_btn);

        setDetails();

        return rootView;
    }

    private void setDetails() {
        if(song != null) {
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
                    SetupActivity.mPlayer.play(song.getUri());
                    MumoApplication.currentlyPlayedSong = song;
                    MusicControllerFragment.startMusic();
                }
            });
        }
    }
}
