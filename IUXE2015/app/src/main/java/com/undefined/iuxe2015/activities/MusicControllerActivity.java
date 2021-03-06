package com.undefined.iuxe2015.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerStateCallback;
import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.SearchFragment;


public class MusicControllerActivity extends MumoActivity {

    public TextView songName;
    private ImageButton playButton,pauseButton;
    public static int oneTimeOnly = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_controller);
        songName = (TextView)findViewById(R.id.textView4);
        playButton = (ImageButton)findViewById(R.id.imageButton1);
        pauseButton = (ImageButton)findViewById(R.id.imageButton2);
        pauseButton.setEnabled(true);
        playButton.setEnabled(false);
        songName.setText(MumoApplication.currentlyPlayedSong.getName());
    }

    public void play(View view){
        toast("Playing sound");
        MumoApplication.mPlayer.resume();
        if(oneTimeOnly == 0){
            oneTimeOnly = 1;
        }

        pauseButton.setEnabled(true);
        playButton.setEnabled(false);
    }

    public void pause(View view){
        toast("Pausing sound");
        MumoApplication.mPlayer.pause();
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
    }
}
