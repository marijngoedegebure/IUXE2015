package com.undefined.iuxe2015.activities;

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
import android.widget.MediaController.MediaPlayerControl;

import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.PlayerStateCallback;
import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.SearchFragment;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.tools.MusicControllerTool;


public class MusicControllerActivity extends MumoActivity implements MediaPlayerControl {

    private MusicControllerTool controller;

    private Boolean playing = false;

    private void setController(){
        //set the controller up
        controller = new MusicControllerTool(this);

        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });

        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.search_list));
        controller.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_controller);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        setController();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_controller, menu);
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

    //play next
    private void playNext(){
        SetupActivity.mPlayer.skipToNext();
        controller.show(0);
    }

    //play previous
    private void playPrev(){
        SetupActivity.mPlayer.skipToPrevious();
        controller.show(0);
    }

    @Override
    public void start() {
        SetupActivity.mPlayer.resume();
    }

    @Override
    public void pause() {
        SetupActivity.mPlayer.pause();
    }

    @Override
    public int getDuration() {
        final Song item = SearchFragment.adapter.getItem(SearchFragment.currentPosition);
        return (int) item.getDuration_ms();
    }

    @Override
    public int getCurrentPosition() {
        return SearchFragment.currentPosition;
    }

    @Override
    public void seekTo(int pos) {
        SetupActivity.mPlayer.seekToPosition(pos);
    }

    @Override
    public boolean isPlaying() {
        playing = false;
        SetupActivity.mPlayer.getPlayerState(new PlayerStateCallback() {
            public void onPlayerState(PlayerState playerState) {
                if(playerState.playing)
                    playing = true;
            }
        });
        return playing;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
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
            View rootView = inflater.inflate(R.layout.fragment_music_controller, container, false);
            return rootView;
        }
    }
}
