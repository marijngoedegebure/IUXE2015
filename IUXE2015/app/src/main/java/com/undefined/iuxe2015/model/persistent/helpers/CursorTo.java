package com.undefined.iuxe2015.model.persistent.helpers;

import android.database.Cursor;

import com.undefined.iuxe2015.model.Rating;

/**
 * Created by Jan-Willem on 22-2-2015.
 */
public class CursorTo {

    public static Rating rating(Cursor cursor, boolean closeCursor) {
        Rating stamp = new Rating();
        stamp.setId(cursor.getInt(0));
        stamp.setSongId(cursor.getString(1));
        stamp.setRating(cursor.getInt(2));
        stamp.setNote(cursor.getString(3));

        if(closeCursor)
            cursor.close();

        return stamp;
    }
}
