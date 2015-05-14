package com.undefined.iuxe2015.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.SongDetailFragment;
import com.undefined.iuxe2015.model.Song;

public class SongDetailActivity extends MumoActivity {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
