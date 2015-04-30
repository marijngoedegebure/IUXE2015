package com.undefined.iuxe2015;

import android.app.Application;
import android.util.Log;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.Spotify;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class MumoApplication extends Application {

    private MumoDataSource data;

    public static Song currentlyPlayedSong;

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
}
