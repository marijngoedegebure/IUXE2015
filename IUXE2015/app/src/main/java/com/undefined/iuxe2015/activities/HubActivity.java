package com.undefined.iuxe2015.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.MusicControllerFragment;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.tools.PreferenceTool;


public class HubActivity extends MumoActivity {

    private static final int REQUEST_CODE = 1028;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setHelpOverlayId(R.id.hub_help);
    }

    public void onButtonClick(View button) {
        Intent intent;
        switch (button.getId()) {
            case R.id.hub_btn_events:
                intent = new Intent(this, EventsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.hub_btn_library:
                intent = new Intent(this, LibraryActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.hub_btn_search:
                intent = new Intent(this, SearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_hub, container, false);
            return rootView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MumoApplication.currentlyPlayedSong != null && requestCode == REQUEST_CODE) {
            MusicControllerFragment f = ((MusicControllerFragment) getSupportFragmentManager().findFragmentById(R.id.music_controller));
            if (f != null)
                f.refresh();
        }
    }
}
