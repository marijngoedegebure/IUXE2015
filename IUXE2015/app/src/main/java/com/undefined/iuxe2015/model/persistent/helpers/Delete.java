package com.undefined.iuxe2015.model.persistent.helpers;

import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Event;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.MumoDbHelper;

/**
 * Created by Jan-Willem on 22-2-2015.
 */
public class Delete {

    private static int delete(SQLiteDatabase database, String table, String columnName, long Id) {
        return database.delete(table, columnName + " = " + Id, null);
    }

    /**
     * RATING
     */

    public static int rating(SQLiteDatabase database, Rating rating) {
        return delete(database, MumoDbHelper.TABLE_RATINGS,
                MumoDbHelper.RATINGS_COLUMN_ID, rating.get_id());
    }

    /**
     * SONG
     */

    public static int song(SQLiteDatabase database, Song song) {
        Delete.imagesOfAlbumOfSong(database, song);
        Delete.albumOfSong(database, song);
        Delete.artistOfSong(database, song);
        return delete(database, MumoDbHelper.TABLE_SONGS,
                MumoDbHelper.SONGS_COLUMN_ID, song.get_id());
    }

    public static int artistOfSong(SQLiteDatabase database, Song song) {
        return delete(database, MumoDbHelper.TABLE_ARTISTS,
                MumoDbHelper.ARTISTS_COLUMN_ID_SONG, song.get_id());
    }

    public static int albumOfSong(SQLiteDatabase database, Song song) {
        return delete(database, MumoDbHelper.TABLE_ALBUMS,
                MumoDbHelper.ALBUMS_COLUMN_ID, song.get_id_album());
    }

    public static int imagesOfAlbumOfSong(SQLiteDatabase database, Song song) {
        return delete(database, MumoDbHelper.TABLE_IMAGES,
                MumoDbHelper.IMAGES_COLUMN_ID_ALBUM, song.get_id_album());
    }

    /**
     * STAKEHOLDER
     */

    public static int stakeholder(SQLiteDatabase database, Stakeholder stakeholder) {
        Delete.ratingsOfStakeholder(database, stakeholder);
        Delete.songsOfStakeholder(database, stakeholder);
        Delete.artistsOfStakeholder(database, stakeholder);
        Delete.albumsOfStakeholder(database, stakeholder);
        Delete.imagesOfStakeholder(database, stakeholder);
        return delete(database, MumoDbHelper.TABLE_STAKEHOLDERS,
                MumoDbHelper.STAKEHOLDERS_COLUMN_ID, stakeholder.get_id());
    }

    public static int ratingsOfStakeholder(SQLiteDatabase database, Stakeholder stakeholder) {
        return delete(database, MumoDbHelper.TABLE_RATINGS,
                MumoDbHelper.RATINGS_COLUMN_ID_STAKEHOLDER, stakeholder.get_id());
    }

    public static int songsOfStakeholder(SQLiteDatabase database, Stakeholder stakeholder) {
        return delete(database, MumoDbHelper.TABLE_RATINGS,
                MumoDbHelper.SONGS_COLUMN_ID_STAKEHOLDER, stakeholder.get_id());
    }

    public static int artistsOfStakeholder(SQLiteDatabase database, Stakeholder stakeholder) {
        return delete(database, MumoDbHelper.TABLE_RATINGS,
                MumoDbHelper.ARTISTS_COLUMN_ID_STAKEHOLDER, stakeholder.get_id());
    }

    public static int albumsOfStakeholder(SQLiteDatabase database, Stakeholder stakeholder) {
        return delete(database, MumoDbHelper.TABLE_RATINGS,
                MumoDbHelper.ALBUMS_COLUMN_ID_STAKEHOLDER, stakeholder.get_id());
    }

    public static int imagesOfStakeholder(SQLiteDatabase database, Stakeholder stakeholder) {
        return delete(database, MumoDbHelper.TABLE_RATINGS,
                MumoDbHelper.IMAGES_COLUMN_ID_STAKEHOLDER, stakeholder.get_id());
    }

    /**
     * EVENT
     */

    public static int event(SQLiteDatabase database, Event event) {
        Delete.ratingsOfEvent(database, event);
        return delete(database, MumoDbHelper.TABLE_STAKEHOLDERS,
                MumoDbHelper.STAKEHOLDERS_COLUMN_ID, event.get_id());
    }

    public static int ratingsOfEvent(SQLiteDatabase database, Event event) {
        return delete(database, MumoDbHelper.TABLE_RATINGS,
                MumoDbHelper.RATINGS_COLUMN_ID_EVENT, event.get_id());
    }
}
