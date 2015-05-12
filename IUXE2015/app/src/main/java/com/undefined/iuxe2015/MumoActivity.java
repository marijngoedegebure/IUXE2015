package com.undefined.iuxe2015;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.dialogs.MusicFinishedDialog;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;
import com.undefined.iuxe2015.model.types.RatingType;
import com.undefined.iuxe2015.tools.PreferenceTool;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public abstract class MumoActivity extends AppCompatActivity {

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

    protected RatingType getRatingType() {
        return PreferenceTool.getRatingType(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(this instanceof SetupActivity)) {
            int stakeholderId = PreferenceTool.getCurrentStakeholderId(this);
            Stakeholder s = getData().getStakeholderWithId(stakeholderId);
            if (s == null)
                Log.e("MumoActivity", "stakeholder " + stakeholderId + " == null!!1!!!");
            else
                super.setTheme(s.getScaleType().getTheme());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((MumoApplication) getApplication()).setCurrentActivity(this);
    }

    public void showRatingDialog(Song song) {
        MusicFinishedDialog newFragment = MusicFinishedDialog.getInstance(song.getId());
        newFragment.show(getSupportFragmentManager(), MusicFinishedDialog.TAG);
    }
}
