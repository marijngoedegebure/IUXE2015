package com.undefined.iuxe2015.activities;

import android.os.Bundle;
import android.view.View;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.LibraryFragment;


public class LibraryActivity extends MumoActivity {

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
}

