package com.undefined.iuxe2015.model.persistent.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDbHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jan-Willem on 22-2-2015.
 */
public class ListAll {
    public static ArrayList<Rating> ratings(SQLiteDatabase database) {
        ArrayList<Rating> ratings = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_RATINGS, MumoDbHelper.allRatingColumns,
                    null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ratings.add(CursorTo.rating(cursor, false));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return ratings;
    }

    public static ArrayList<Rating> ratingsForSong(SQLiteDatabase database, Song song) {
        ArrayList<Rating> ratings = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_RATINGS, MumoDbHelper.allRatingColumns,
                    MumoDbHelper.RATINGS_COLUMN_ID_SONG + " = " + song.getId(),
                    null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ratings.add(CursorTo.rating(cursor, false));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return ratings;
    }
}
