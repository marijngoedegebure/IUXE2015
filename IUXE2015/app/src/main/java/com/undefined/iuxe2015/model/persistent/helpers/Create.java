package com.undefined.iuxe2015.model.persistent.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Album;
import com.undefined.iuxe2015.model.Artist;
import com.undefined.iuxe2015.model.Image;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.MumoDbHelper;

/**
 * Created by Jan-Willem on 22-2-2015.
 */
public class Create {

    public static Rating rating(SQLiteDatabase database, String songId, int rating, String note) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.RATINGS_COLUMN_ID_SONG, songId);
        values.put(MumoDbHelper.RATINGS_COLUMN_RATING, rating);
        values.put(MumoDbHelper.RATINGS_COLUMN_NOTE, note);
        values.put(MumoDbHelper.RATINGS_COLUMN_TIMESTAMP, System.currentTimeMillis());
        long insertId = database.insert(MumoDbHelper.TABLE_RATINGS, null, values);

        Cursor cursor = database.query(MumoDbHelper.TABLE_RATINGS,
                MumoDbHelper.allRatingColumns, MumoDbHelper.RATINGS_COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        return CursorTo.rating(cursor, true);
    }

    public static Song song(SQLiteDatabase database, Song song) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.SONGS_COLUMN_ID_ALBUM, song.get_id_album());
        values.put(MumoDbHelper.SONGS_COLUMN_ID_SONG, song.getId());
        values.put(MumoDbHelper.SONGS_COLUMN_NAME, song.getName());
        values.put(MumoDbHelper.SONGS_COLUMN_URI, song.getUri());
        values.put(MumoDbHelper.SONGS_COLUMN_DURATION_MS, song.getDurationMs());
        long insertId = database.insert(MumoDbHelper.TABLE_SONGS, null, values);

        Cursor cursor = database.query(MumoDbHelper.TABLE_SONGS,
                MumoDbHelper.allSongColumns, MumoDbHelper.SONGS_COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        return CursorTo.song(cursor, true);
    }

    public static Artist artist(SQLiteDatabase database, Artist artist) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.ARTISTS_COLUMN_ID_SONG, artist.get_id_song());
        values.put(MumoDbHelper.ARTISTS_COLUMN_ID_ARTIST, artist.getId());
        values.put(MumoDbHelper.ARTISTS_COLUMN_NAME, artist.getName());
        long insertId = database.insert(MumoDbHelper.TABLE_ARTISTS, null, values);

        Cursor cursor = database.query(MumoDbHelper.TABLE_ARTISTS,
                MumoDbHelper.allArtistColumns, MumoDbHelper.ARTISTS_COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        return CursorTo.artist(cursor, true);
    }

    public static Album album(SQLiteDatabase database, Album album) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.ALBUMS_COLUMN_ID_ALBUM, album.getId());
        values.put(MumoDbHelper.ALBUMS_COLUMN_NAME, album.getName());
        long insertId = database.insert(MumoDbHelper.TABLE_ALBUMS, null, values);

        Cursor cursor = database.query(MumoDbHelper.TABLE_ALBUMS,
                MumoDbHelper.allAlbumColumns, MumoDbHelper.ALBUMS_COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        return CursorTo.album(cursor, true);
    }

    public static Image image(SQLiteDatabase database, Image image) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.IMAGES_COLUMN_ID_ALBUM, image.get_id_album());
        values.put(MumoDbHelper.IMAGES_COLUMN_HEIGHT, image.getHeight());
        values.put(MumoDbHelper.IMAGES_COLUMN_WIDTH, image.getWidth());
        values.put(MumoDbHelper.IMAGES_COLUMN_URL, image.getUrl());
        long insertId = database.insert(MumoDbHelper.TABLE_IMAGES, null, values);

        Cursor cursor = database.query(MumoDbHelper.TABLE_IMAGES,
                MumoDbHelper.allImageColumns, MumoDbHelper.IMAGES_COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        return CursorTo.image(cursor, true);
    }

    public static Stakeholder stakeholder(SQLiteDatabase database, String name, int age) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.STAKEHOLDERS_COLUMN_NAME, name);
        values.put(MumoDbHelper.STAKEHOLDERS_COLUMN_AGE, age);
        long insertId = database.insert(MumoDbHelper.TABLE_STAKEHOLDERS, null, values);

        Cursor cursor = database.query(MumoDbHelper.TABLE_STAKEHOLDERS,
                MumoDbHelper.allStakeholderColumns, MumoDbHelper.STAKEHOLDERS_COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        return CursorTo.stakeholder(cursor, true);
    }
}
