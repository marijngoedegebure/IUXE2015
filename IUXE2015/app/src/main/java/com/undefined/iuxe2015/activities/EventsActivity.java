package com.undefined.iuxe2015.activities;

import android.os.Bundle;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.EventsFragment;
import com.undefined.iuxe2015.fragments.SearchFragment;


public class EventsActivity extends MumoActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new EventsFragment())
                    .commit();
        }
    }
}
