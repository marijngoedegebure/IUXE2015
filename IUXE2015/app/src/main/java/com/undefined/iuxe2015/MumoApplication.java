package com.undefined.iuxe2015;

import android.app.Application;

import com.undefined.iuxe2015.model.persistent.MumoDataSource;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class MumoApplication extends Application {

    MumoDataSource data;

    @Override
    public void onCreate() {
        data = new MumoDataSource(this);
        data.open();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        data.close();
        data = null;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        data.close();
        data = null;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        data.close();
        data = null;
    }

    public MumoDataSource getData() {
        if (data == null) {
            data = new MumoDataSource(this);
            data.open();
        }
        return data;
    }
}
