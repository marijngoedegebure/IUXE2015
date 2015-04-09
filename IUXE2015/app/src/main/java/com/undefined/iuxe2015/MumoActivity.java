package com.undefined.iuxe2015;

import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.undefined.iuxe2015.model.persistent.MumoDataSource;
import com.undefined.iuxe2015.model.types.RatingType;
import com.undefined.iuxe2015.model.types.ScaleType;
import com.undefined.iuxe2015.tools.PreferenceTool;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public abstract class MumoActivity extends ActionBarActivity {

    private Toast t;

    protected MumoDataSource getData() {
        return ((MumoApplication) getApplication()).getData();
    }

    protected void toast(String message) {
        if (t == null)
            t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.setText(message);
        t.show();
    }

    protected ScaleType getScaleType(){
        return PreferenceTool.getScaleType(this);
    }

    protected RatingType getRatingType(){
        return PreferenceTool.getRatingType(this);
    }
}
