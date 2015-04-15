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

    public static final String TABLE_SONGS = "songs";
    public static final String SONGS_COLUMN_ID = "_dbid_song";
    public static final String SONGS_COLUMN_ID_SONG = "_id_song";
    public static final String SONGS_COLUMN_NAME = "name";
    public static final String SONGS_COLUMN_URI = "uri";
    public static final String SONGS_COLUMN_DURATION_MS = "duration_ms";
    public static final String[] allSongColumns = {
            MumoDbHelper.SONGS_COLUMN_ID,
            MumoDbHelper.SONGS_COLUMN_ID_SONG,
            MumoDbHelper.SONGS_COLUMN_NAME,
            MumoDbHelper.SONGS_COLUMN_URI,
            MumoDbHelper.SONGS_COLUMN_DURATION_MS};

    public static final String TABLE_ARTISTS = "artists";
    public static final String ARTISTS_COLUMN_ID = "_id_artist";
    public static final String ARTISTS_COLUMN_ID_SONG = "_id_song";
    public static final String ARTISTS_COLUMN_NAME = "name";
    public static final String[] allArtistColumns = {
            MumoDbHelper.ARTISTS_COLUMN_ID,
            MumoDbHelper.ARTISTS_COLUMN_ID_SONG,
            MumoDbHelper.ARTISTS_COLUMN_NAME};

    public static final String TABLE_ALBUMS = "albums";
    public static final String ALBUMS_COLUMN_ID = "_id";
    public static final String ALBUMS_COLUMN_ID_ALBUM = "_id_album";
    public static final String ALBUMS_COLUMN_ID_SONG = "_id_song";
    public static final String ALBUMS_COLUMN_NAME = "name";
    public static final String[] allAlbumColumns = {
            MumoDbHelper.ALBUMS_COLUMN_ID,
            MumoDbHelper.ALBUMS_COLUMN_ID_ALBUM,
            MumoDbHelper.ALBUMS_COLUMN_ID_SONG,
            MumoDbHelper.ALBUMS_COLUMN_NAME};

    public static final String TABLE_IMAGES = "images";
    public static final String IMAGES_COLUMN_ID = "_id_image";
    public static final String IMAGES_COLUMN_ID_ALBUM = "_id_album";
    public static final String IMAGES_COLUMN_WIDTH = "width";
    public static final String IMAGES_COLUMN_HEIGHT = "height";
    public static final String IMAGES_COLUMN_URL = "url";
    public static final String[] allImageColumns = {
            MumoDbHelper.IMAGES_COLUMN_ID,
            MumoDbHelper.IMAGES_COLUMN_ID_ALBUM,
            MumoDbHelper.IMAGES_COLUMN_WIDTH,
            MumoDbHelper.IMAGES_COLUMN_HEIGHT,
            MumoDbHelper.IMAGES_COLUMN_URL};

    // Database creation sql statement
    private static final String DATABASE_CREATE_RATINGS = "create table "
            + TABLE_RATINGS + "( "
            + RATINGS_COLUMN_ID + " integer primary key autoincrement, "
            + RATINGS_COLUMN_ID_SONG + " text not null, "
            + RATINGS_COLUMN_RATING + " integer, "
            + RATINGS_COLUMN_NOTE + " text not null, "
            + RATINGS_COLUMN_TIMESTAMP + " integer);";

    private static final String DATABASE_CREATE_SONGS = "create table "
            + TABLE_SONGS + "( "
            + SONGS_COLUMN_ID + " integer primary key autoincrement, "
            + SONGS_COLUMN_ID_SONG + " text not null, "
            + SONGS_COLUMN_NAME + " text not null, "
            + SONGS_COLUMN_URI + " text not null, "
            + SONGS_COLUMN_DURATION_MS + " integer);";

    private static final String DATABASE_CREATE_ARTISTS = "create table "
            + TABLE_ARTISTS + "( "
            + ARTISTS_COLUMN_ID + " integer primary key autoincrement, "
            + ARTISTS_COLUMN_ID_SONG + " integer, "
            + ARTISTS_COLUMN_NAME + " text not null);";

    private static final String DATABASE_CREATE_ALBUMS = "create table "
            + TABLE_ALBUMS + "( "
            + ALBUMS_COLUMN_ID + " integer primary key autoincrement, "
            + ALBUMS_COLUMN_ID_ALBUM + " integer, "
            + ALBUMS_COLUMN_ID_SONG + " integer, "
            + ALBUMS_COLUMN_NAME + " text not null);";

    private static final String DATABASE_CREATE_IMAGES = "create table "
            + TABLE_IMAGES + "( "
            + IMAGES_COLUMN_ID + " integer primary key autoincrement, "
            + IMAGES_COLUMN_ID_ALBUM + " text not null, "
            + IMAGES_COLUMN_WIDTH + " integer, "
            + IMAGES_COLUMN_HEIGHT + " integer, "
            + IMAGES_COLUMN_URL + " text not null);";

    public MumoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_RATINGS);
        database.execSQL(DATABASE_CREATE_SONGS);
        database.execSQL(DATABASE_CREATE_ARTISTS);
        database.execSQL(DATABASE_CREATE_ALBUMS);
        database.execSQL(DATABASE_CREATE_IMAGES);
    }

    public void clear(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBUMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
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

