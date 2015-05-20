package com.undefined.iuxe2015.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.dialogs.EventDialog;
import com.undefined.iuxe2015.fragments.EventsFragment;
import com.undefined.iuxe2015.fragments.SongDetailFragment;
import com.undefined.iuxe2015.model.Song;

public class SongDetailActivity extends MumoActivity implements EventDialog.eventDialogListener {

    public static final String EXTRA_SONGID = "songId";

    public static Intent getStartIntent(Context c, Song song) {
        Intent intent = new Intent(c, SongDetailActivity.class);
        intent.putExtra(EXTRA_SONGID, song.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            String songId = getIntent().getStringExtra(EXTRA_SONGID);
            SongDetailFragment f = new SongDetailFragment();
            Bundle args = new Bundle();
            args.putString(EXTRA_SONGID, songId);
            f.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.song_detail_container, f, SongDetailFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setHelpOverlayIds(R.id.songdetail_help_1);
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
            View rootView = inflater.inflate(R.layout.fragment_song_detail, container, false);
            return rootView;
        }
    }
}
