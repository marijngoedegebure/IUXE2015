package com.undefined.iuxe2015.model.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class MumoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mumo.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_RATINGS = "ratings";
    public static final String RATINGS_COLUMN_ID = "_id_rating";
    public static final String RATINGS_COLUMN_ID_SONG = "_id_song";
    public static final String RATINGS_COLUMN_RATING = "rating";
    public static final String RATINGS_COLUMN_NOTE = "note";
    public static final String RATINGS_COLUMN_TIMESTAMP = "timestamp";
    public static final String[] allRatingColumns = {
            MumoDbHelper.RATINGS_COLUMN_ID,
            MumoDbHelper.RATINGS_COLUMN_ID_SONG,
            MumoDbHelper.RATINGS_COLUMN_RATING,
            MumoDbHelper.RATINGS_COLUMN_NOTE,
            MumoDbHelper.RATINGS_COLUMN_TIMESTAMP};

    // Database creation sql statement
    private static final String DATABASE_CREATE_RATINGS = "create table "
            + TABLE_RATINGS + "( "
            + RATINGS_COLUMN_ID + " integer primary key autoincrement, "
            + RATINGS_COLUMN_ID_SONG + " text not null, "
            + RATINGS_COLUMN_RATING + " integer, "
            + RATINGS_COLUMN_NOTE + " text not null, "
            + RATINGS_COLUMN_TIMESTAMP + " integer);";

    public MumoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_RATINGS);
    }

    public void clear(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MumoDbHelper
                        .class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        clear(db);
    }
}

