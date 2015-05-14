package com.undefined.iuxe2015.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.spotify.sdk.android.player.Player;
import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;

public class MusicControllerFragment extends MumoFragment {


    public static TextView songName;
    private static ImageButton playButton, pauseButton;

    public MusicControllerFragment() {
        // Required empty public constructor
    }

    public static void startMusic() {
        songName.setText(MumoApplication.currentlyPlayedSong.getName());
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music_controller, container, false);
        songName = (TextView)rootView.findViewById(R.id.textView4);
        playButton = (ImageButton)rootView.findViewById(R.id.imageButton1);
        pauseButton = (ImageButton)rootView.findViewById(R.id.imageButton2);
        pauseButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   SetupActivity.mPlayer.pause();
                   pauseButton.setEnabled(false);
                   playButton.setEnabled(true);
               }
           });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupActivity.mPlayer.resume();
                pauseButton.setEnabled(true);
                playButton.setEnabled(false);
            }
        });
        if(MumoApplication.currentlyPlayedSong != null) {
            songName.setText(MumoApplication.currentlyPlayedSong.getName());
            pauseButton.setEnabled(true);
            playButton.setEnabled(false);
        }
        else {
            pauseButton.setEnabled(false);
            playButton.setEnabled(false);
        }
        return rootView;
    }
}
