package com.undefined.iuxe2015.model.persistent.helpers;

import android.database.Cursor;

import com.undefined.iuxe2015.model.Image;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Album;
import com.undefined.iuxe2015.model.Artist;
import com.undefined.iuxe2015.model.Stakeholder;

/**
 * Created by Jan-Willem on 22-2-2015.
 */
public class CursorTo {

    public static Rating rating(Cursor cursor, boolean closeCursor) {
        Rating stamp = new Rating();
        stamp.set_id(cursor.getInt(0));
        stamp.set_id_song(cursor.getInt(1));
        stamp.setRating(cursor.getInt(2));
        stamp.setNote(cursor.getString(3));
        stamp.setTimestamp(cursor.getLong(4));

        if(closeCursor)
            cursor.close();

        return stamp;
    }

    public static Song song(Cursor cursor, boolean closeCursor) {
        Song song = new Song();
        song.set_id(cursor.getInt(0));
        song.set_id_album(cursor.getInt(1));
        song.setId(cursor.getString(2));
        song.setName(cursor.getString(3));
        song.setUri(cursor.getString(4));
        song.setDurationMs(cursor.getLong(5));

        if(closeCursor)
            cursor.close();

        return song;
    }

    public static Artist artist(Cursor cursor, boolean closeCursor) {
        Artist artist = new Artist();
        artist.set_id(cursor.getInt(0));
        artist.set_id_song(cursor.getInt(1));
        artist.setId(cursor.getString(2));
        artist.setName(cursor.getString(3));

        if(closeCursor)
            cursor.close();

        return artist;
    }

    public static Album album(Cursor cursor, boolean closeCursor) {
        Album album = new Album();
        album.set_id(cursor.getInt(0));
        album.setId(cursor.getString(1));
        album.setName(cursor.getString(2));

        if(closeCursor)
            cursor.close();

        return album;
    }

    public static Image image(Cursor cursor, boolean closeCursor) {
        Image image = new Image();
        image.set_id(cursor.getInt(0));
        image.set_id_album(cursor.getInt(1));
        image.setHeight(cursor.getInt(2));
        image.setWidth(cursor.getInt(3));
        image.setUrl(cursor.getString(4));

        if(closeCursor)
            cursor.close();

        return image;
    }

    public static Stakeholder stakeholder(Cursor cursor, boolean closeCursor) {
        Stakeholder stakeholder = new Stakeholder();
        stakeholder.set_id(cursor.getInt(0));
        stakeholder.setName(cursor.getString(1));
        stakeholder.setAge(cursor.getInt(2));
        stakeholder.setPrefFontSize(cursor.getInt(3));

        if(closeCursor)
            cursor.close();

        return stakeholder;
    }
}
