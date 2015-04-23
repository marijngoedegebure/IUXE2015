package com.undefined.iuxe2015.model.types;

import com.undefined.iuxe2015.R;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public enum ScaleType {
    NORMAL(R.style.AppTheme_Normal),
    LARGE(R.style.AppTheme_Large),
    HUGE(R.style.AppTheme_Huge);

    private int theme;

    ScaleType(int theme) {
        this.theme = theme;
    }

    public int getTheme() {
        return theme;
    }

    public static ScaleType getDefault() {
        return NORMAL;
    }
}
