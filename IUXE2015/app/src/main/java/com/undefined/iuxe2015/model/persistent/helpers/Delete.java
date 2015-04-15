package com.undefined.iuxe2015.model.persistent.helpers;

import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDbHelper;
import com.undefined.iuxe2015.model.Rating;

/**
 * Created by Jan-Willem on 22-2-2015.
 */
public class Delete {

    private static int delete(SQLiteDatabase database, String table, String columnName, long Id) {
        return database.delete(table, columnName + " = " + Id, null);
    }

    public static int rating(SQLiteDatabase database, Rating rating) {
        return delete(database, MumoDbHelper.TABLE_RATINGS, MumoDbHelper.RATINGS_COLUMN_ID, rating.getId());
    }

    public static int song(SQLiteDatabase database, Song song) {
        //TODO Delete Album
        //TODO Delete Album Images
        //TODO Delete Artist
        return delete(database, MumoDbHelper.TABLE_SONGS, MumoDbHelper.SONGS_COLUMN_ID, song.getDbId());
    }
}
