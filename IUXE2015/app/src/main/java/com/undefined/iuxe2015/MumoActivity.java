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
import com.undefined.iuxe2015.dialogs.RateSongDialog;
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

    private final int overlayFadeMs = 300;
    protected View helpOverlay;

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
            if (helpOverlay != null && helpOverlay.getVisibility() == View.VISIBLE) {
                hideOverlay(null);
            } else {
                finish();
            }
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (helpOverlay != null && helpOverlay.getVisibility() == View.VISIBLE) {
            hideOverlay(null);
        } else {
            super.onBackPressed();
        }
    }

    public void showRatingDialog(Song song) {
        RateSongDialog newFragment = RateSongDialog.getInstance(song.getId());
        newFragment.show(getSupportFragmentManager(), RateSongDialog.TAG);
    }

    protected void setHelpOverlayId(@IdRes int overlayViewId) {
        this.helpOverlay = findViewById(overlayViewId);
        //Make sure its hidden on start
        hideOverlay(null);
    }

    public void showOverlay(View v) {
        if (helpOverlay != null) {
            AlphaAnimation a = new AlphaAnimation(0, 1);
            a.setDuration(overlayFadeMs);
            helpOverlay.setAnimation(a);
            helpOverlay.setVisibility(View.VISIBLE);
            a.start();
            //TODO hide keyboard if visible
        }
    }

    public void hideOverlay(View v) {
        if (helpOverlay != null) {
            AlphaAnimation a = new AlphaAnimation(1, 0);
            a.setDuration(overlayFadeMs);
            helpOverlay.setAnimation(a);
            a.start();
            helpOverlay.setVisibility(View.GONE);
        }
    }
}
