package com.undefined.iuxe2015.model.persistent.helpers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.persistent.MumoDbHelper;

/**
 * Created by Jan-Willem on 22-2-2015.
 */
public class Update {
    public static Rating rating(SQLiteDatabase database, Rating rating) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.RATINGS_COLUMN_ID_SONG, rating.getSongId());
        values.put(MumoDbHelper.RATINGS_COLUMN_RATING, rating.getRating());
        values.put(MumoDbHelper.RATINGS_COLUMN_NOTE, rating.getNote());
        values.put(MumoDbHelper.RATINGS_COLUMN_TIMESTAMP, System.currentTimeMillis());
        database.update(MumoDbHelper.TABLE_RATINGS, values,
                MumoDbHelper.RATINGS_COLUMN_ID + " = " + rating.getId(), null);
        return rating;
    }
}
