package com.undefined.iuxe2015.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.adapters.SongSearchAdapter;
import com.undefined.iuxe2015.model.Artist;
import com.undefined.iuxe2015.model.QueryResult;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.tools.ConnectionTool;

/**
 * Created by marijngoedegebure on 23-04-15.
 */
public class SongDetailFragment extends MumoFragment {

    TextView name;
    TextView album;
    TextView artist;
    Button playSong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song_detail, container, false);

        name = (TextView) rootView.findViewById(R.id.song_detail_name);
        album = (TextView) rootView.findViewById(R.id.song_detail_album);
        artist = (TextView) rootView.findViewById(R.id.song_detail_artist);
        playSong = (Button) rootView.findViewById(R.id.song_detail_play_btn);

        final Song currentSong = SearchFragment.adapter.getItem(SearchFragment.currentPosition);
        name.setText(currentSong.getName());
        album.setText(currentSong.getAlbum().getName());
        if(currentSong.getArtists().size() > 0) {
            String artistText = currentSong.getArtists().get(0).getName();
            for (int i = 1; i < currentSong.getArtists().size(); i++) {
                artistText += ", " + currentSong.getArtists().get(i).getName();
            }
            artist.setText(artistText);
        }
        else {
            artist.setText("No artists found");
        }

        playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupActivity.mPlayer.play(currentSong.getUri());
            }
        });
        return rootView;
    }
}
