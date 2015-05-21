package com.undefined.iuxe2015.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.LibraryFragment;
import com.undefined.iuxe2015.fragments.MusicControllerFragment;


public class LibraryActivity extends MumoActivity {

    public static final int REQUEST_CODE = 3350;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LibraryFragment())
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setHelpOverlayIds(R.id.library_help);
    }

    public void onButtonClick(View button) {
        switch (button.getId()) {
            case R.id.song_artist:
                toast("TODO: Start Help Overlay");
                //TODO start Help Overlay
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MumoApplication.currentlyPlayedSong != null && requestCode == REQUEST_CODE)
            ((MusicControllerFragment) getSupportFragmentManager().findFragmentById(R.id.music_controller)).refresh();
    }
}

