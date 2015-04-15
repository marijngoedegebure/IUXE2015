package com.undefined.iuxe2015.model.persistent.helpers;

import android.database.Cursor;

import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Album;
import com.undefined.iuxe2015.model.Artist;

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
        stamp.setTimestamp(cursor.getLong(4));

        if(closeCursor)
            cursor.close();

        return stamp;
    }

    public static Song song(Cursor cursor, boolean closeCursor) {
        Song song = new Song();
        song.setId(cursor.getInt(0));
        song.setSongId(cursor.getString(1));
        song.setName(cursor.getString(2));
        song.setUri(cursor.getString(3));
        song.setDurationMs(cursor.getLong(4));

        if(closeCursor)
            cursor.close();

        return song;
    }

    public static Artist artist(Cursor cursor, boolean closeCursor) {
        Artist artist = new Artist();
        artist.setId(cursor.getInt(0));
        artist.setSongId(cursor.getString(1));
        artist.setName(cursor.getString(2));

        if(closeCursor)
            cursor.close();

        return artist;
    }

    public static Album album(Cursor cursor, boolean closeCursor) {
        Album album = new Album();
        album.setDbId(cursor.getInt(0));
        album.setSongId(cursor.getString(1));
        album.setName(cursor.getString(2));

        if(closeCursor)
            cursor.close();

        return album;
    }
}
