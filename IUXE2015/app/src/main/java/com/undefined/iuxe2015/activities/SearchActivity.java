package com.undefined.iuxe2015.activities;

import android.content.Intent;
import android.os.Bundle;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.MusicControllerFragment;


public class SearchActivity extends MumoActivity{

    public static final int REQUEST_CODE = 1028;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setHelpOverlayIds(R.id.search_help_1, R.id.search_help_2, R.id.help_controller);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MumoApplication.currentlyPlayedSong != null && requestCode == REQUEST_CODE)
            ((MusicControllerFragment) getSupportFragmentManager().findFragmentById(R.id.music_controller)).refresh();
    }
}
