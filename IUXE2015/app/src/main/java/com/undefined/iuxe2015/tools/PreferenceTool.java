package com.undefined.iuxe2015.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.undefined.iuxe2015.model.types.RatingType;
import com.undefined.iuxe2015.model.types.ScaleType;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class PreferenceTool {

    private static final String PREFS_NAME = "preferences";
    private static final String KEY_CURRENT_STAKEHOLDER_ID = "currentStakeholderId";
    private static final String KEY_TYPE_RATING = "ratingType";

    public static final int DEFAULT_STAKEHOLDER_ID = -1;

    private static SharedPreferences getPrefs(Activity a) {
        return a.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Activity a) {
        return getPrefs(a).edit();
    }

    public static void setRatingType(Activity a, RatingType r) {
        getEditor(a).putString(KEY_TYPE_RATING, r.name()).apply();
    }

    public static RatingType getRatingType(Activity a) {
        String ratingName = getPrefs(a).getString(KEY_TYPE_RATING, RatingType.getDefault().name());
        try {
            return RatingType.valueOf(ratingName);
        } catch (IllegalArgumentException e) {
            Log.e("Preferences", "Illegal RatingType in Preferences: " + e.getLocalizedMessage());
            return RatingType.getDefault();
        }
    }

    public static void setCurrentStakeholderId(Activity a, int stakeholderId) {
        Log.d("PreferenceTool", "setCurrentStakeholderId: " + stakeholderId);
        getEditor(a).putInt(KEY_CURRENT_STAKEHOLDER_ID, stakeholderId).apply();
    }

    public static int getCurrentStakeholderId(Activity a) {
        return getPrefs(a).getInt(KEY_CURRENT_STAKEHOLDER_ID, DEFAULT_STAKEHOLDER_ID);
    }
}
