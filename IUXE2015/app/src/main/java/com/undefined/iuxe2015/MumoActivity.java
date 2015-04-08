package com.undefined.iuxe2015;

import android.support.v7.app.ActionBarActivity;

import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public abstract class MumoActivity extends ActionBarActivity {

    protected MumoDataSource getData(){
        return ((MumoApplication) getApplication()).getData();
    }
}
