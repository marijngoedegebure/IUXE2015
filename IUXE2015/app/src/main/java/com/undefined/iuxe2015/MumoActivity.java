package com.undefined.iuxe2015;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.dialogs.EventDialog;
import com.undefined.iuxe2015.dialogs.RateSongDialog;
import com.undefined.iuxe2015.fragments.EventsFragment;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;
import com.undefined.iuxe2015.model.types.RatingType;
import com.undefined.iuxe2015.tools.PreferenceTool;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public abstract class MumoActivity extends AppCompatActivity implements EventDialog.eventDialogListener {

    private static final String TAG = "MumoActivity";
    private Toast t;

    private final int overlayFadeMs = 300;
    protected ArrayList<View> helpOverlays;
    protected int helpIndex = 0;

    public MumoDataSource getData() {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (helpOverlays != null && helpOverlays.get(helpIndex) != null && helpOverlays.get(helpIndex).getVisibility() == View.VISIBLE) {
                previousOverlay(null);
            } else {
                finish();
            }
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (helpOverlays != null && helpOverlays.get(helpIndex) != null && helpOverlays.get(helpIndex).getVisibility() == View.VISIBLE) {
            previousOverlay(null);
        } else {
            super.onBackPressed();
        }
    }

    public void showRatingDialog(Song song) {
        if (song != null) {
            RateSongDialog newFragment = RateSongDialog.getInstance(song.getId());
            newFragment.show(getSupportFragmentManager(), RateSongDialog.TAG);
        }
    }

    protected void setHelpOverlayIds(@IdRes int... overlayViewIds) {
        helpOverlays = new ArrayList<>();
        for (int overlayViewId : overlayViewIds) {
            this.helpOverlays.add(findViewById(overlayViewId));
        }
        //Make sure its hidden on start
        hideOverlays(null);
    }

    public void showOverlay(View v) {
        Log.d(TAG, "showOverlay(View v)");
        showOverlay(0, -1);
    }

    public void showOverlay(int index, int previous) {
        Log.d(TAG, "showOverlay("+index+", "+previous+")");
        if (helpOverlays == null) {
            return;
        }

        if (previous >= 0 && helpOverlays.get(previous) != null) {
            hideOverlay(previous);
        }

        if (helpOverlays != null && index > -1 && index < helpOverlays.size() && helpOverlays.get(index) != null) {
            helpIndex = index;
            View helpOverlay = helpOverlays.get(index);
            //AlphaAnimation a = new AlphaAnimation(0, 1);
            //a.setDuration(overlayFadeMs);
            //helpOverlay.setAnimation(a);
            helpOverlay.setVisibility(View.VISIBLE);
            //a.start();
            //TODO hide keyboard if visible
        } else {
            hideOverlays(null);
        }
    }

    public void previousOverlay(View v) {
        showOverlay(helpIndex - 1, helpIndex);
    }

    public void nextOverlay(View v) {
        showOverlay(helpIndex + 1, helpIndex);
    }

    public void hideOverlays(View v) {
        Log.d(TAG, "hideOverlays(View v)");
        for (int i = 0; i < helpOverlays.size(); i++) {
            hideOverlay(i);
        }
        helpIndex = 0;
    }

    public void hideOverlay(int index) {
        Log.d(TAG, "hideOverlay("+index+")");
        if (helpOverlays != null && helpOverlays.get(index) != null) {
            View helpOverlay = helpOverlays.get(index);
            //AlphaAnimation a = new AlphaAnimation(1, 0);
            //a.setDuration(overlayFadeMs);
            //helpOverlay.setAnimation(a);
            //a.start();
            helpOverlay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEventDialogClosed(int event_id) {
        RateSongDialog e = (RateSongDialog) getSupportFragmentManager().findFragmentByTag(RateSongDialog.TAG);
        if (e != null) {
            e.onEventDialogClose(event_id);
        } else {
            //TODO handle
        }
    }
}
