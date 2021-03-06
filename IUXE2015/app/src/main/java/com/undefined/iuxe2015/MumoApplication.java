package com.undefined.iuxe2015;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;


/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class MumoApplication extends Application implements
        PlayerNotificationCallback {

    private MumoDataSource data;

    private MumoActivity currentActivity;

    public static Song currentlyPlayedSong;
    public static Player mPlayer;

    @Override
    public void onCreate() {
        data = new MumoDataSource(this);
        data.open();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (data != null) {
            data.close();
            data = null;
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (data != null) {
            data.close();
            data = null;
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (data != null) {
            data.close();
            data = null;
        }
    }

    public MumoDataSource getData() {
        if (data == null) {
            data = new MumoDataSource(this);
            data.open();
        }
        return data;
    }

    public void setCurrentActivity(MumoActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    public void onPlaybackEvent(final EventType eventType, PlayerState playerState) {
        Log.d("SetupActivity", "Playback event received: " + eventType.name());
        //TODO stop delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (eventType == EventType.TRACK_CHANGED) {
                    //currentActivity.showRatingDialog(previouslyPlayedSong);
                } else if (eventType == EventType.TRACK_END) {
                    currentlyPlayedSong = null;
                }
                else if (eventType == EventType.TRACK_START) {
                }
            }
        }, 1000);
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("SetupActivity", "Playback error received: " + errorType.name());
    }
}
