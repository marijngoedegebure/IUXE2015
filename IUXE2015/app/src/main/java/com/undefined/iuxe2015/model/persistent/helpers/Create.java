package com.undefined.iuxe2015.model.persistent.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.persistent.MumoDbHelper;
import com.undefined.iuxe2015.model.Rating;

/**
 * Created by Jan-Willem on 22-2-2015.
 */
public class Create {

    public static Rating rating(SQLiteDatabase database, long songId, int rating, String note) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.RATINGS_COLUMN_ID_SONG, songId);
        values.put(MumoDbHelper.RATINGS_COLUMN_RATING, rating);
        values.put(MumoDbHelper.RATINGS_COLUMN_NOTE, note);
        long insertId = database.insert(MumoDbHelper.TABLE_RATINGS, null, values);

        Cursor cursor = database.query(MumoDbHelper.TABLE_RATINGS,
                MumoDbHelper.allRatingColumns, MumoDbHelper.RATINGS_COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        return CursorTo.rating(cursor, true);
    }
}
