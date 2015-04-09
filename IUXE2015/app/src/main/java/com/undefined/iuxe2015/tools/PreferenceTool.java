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

    private static final String KEY_TYPE_SCALE = "scaleType";
    private static final String KEY_TYPE_RATING = "ratingType";

    private static SharedPreferences getPrefs(Activity a) {
        return a.getPreferences(Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Activity a) {
        return getPrefs(a).edit();
    }

    public static void setScaleType(Activity a, ScaleType s) {
        getEditor(a).putString(KEY_TYPE_SCALE, s.name()).apply();
    }

    public static ScaleType getScaleType(Activity a) {
        String scaleName = getPrefs(a).getString(KEY_TYPE_SCALE, ScaleType.getDefault().name());
        try {
            return ScaleType.valueOf(scaleName);
        } catch (IllegalArgumentException e) {
            Log.e("Preferences", "Illegal ScaleType in Preferences: " + e.getLocalizedMessage());
            return ScaleType.getDefault();
        }
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
}
