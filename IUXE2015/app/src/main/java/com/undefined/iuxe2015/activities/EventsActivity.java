package com.undefined.iuxe2015.activities;

import android.content.Intent;
import android.os.Bundle;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.dialogs.EventDialog;
import com.undefined.iuxe2015.fragments.EventsFragment;
import com.undefined.iuxe2015.fragments.MusicControllerFragment;
import com.undefined.iuxe2015.fragments.SearchFragment;


public class EventsActivity extends MumoActivity implements EventDialog.eventDialogListener {

    private static final String TAG = "EventsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new EventsFragment(), EventsFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setHelpOverlayIds(R.id.events_help);
    }

    @Override
    public void onEventDialogClosed() {
        EventsFragment e = (EventsFragment) getSupportFragmentManager().findFragmentByTag(EventsFragment.TAG);
        if (e != null) {
            e.onEventDialogClose();
        } else {
            //TODO handle
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MumoApplication.currentlyPlayedSong != null)
            ((MusicControllerFragment) getSupportFragmentManager().findFragmentById(R.id.music_controller)).refresh();
    }
}
