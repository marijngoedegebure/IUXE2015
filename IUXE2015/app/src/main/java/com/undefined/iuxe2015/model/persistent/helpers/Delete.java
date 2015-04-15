package com.undefined.iuxe2015.model.persistent.helpers;

import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Artist;
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
        return delete(database, MumoDbHelper.TABLE_RATINGS, MumoDbHelper.RATINGS_COLUMN_ID, rating.get_id());
    }

    public static int song(SQLiteDatabase database, Song song) {
        Delete.imagesOfAlbumOfSong(database, song);
        Delete.albumOfSong(database, song);
        Delete.artistOfSong(database, song);
        return delete(database, MumoDbHelper.TABLE_SONGS, MumoDbHelper.SONGS_COLUMN_ID, song.get_id());
    }

    public static int artistOfSong(SQLiteDatabase database, Song song) {
        return delete(database, MumoDbHelper.TABLE_ARTISTS, MumoDbHelper.ARTISTS_COLUMN_ID_SONG, song.get_id());
    }

    public static int albumOfSong(SQLiteDatabase database, Song song) {
        return delete(database, MumoDbHelper.TABLE_ALBUMS, MumoDbHelper.ALBUMS_COLUMN_ID, song.get_id_album());
    }

    public static int imagesOfAlbumOfSong(SQLiteDatabase database, Song song) {
        return delete(database, MumoDbHelper.TABLE_IMAGES, MumoDbHelper.IMAGES_COLUMN_ID_ALBUM, song.get_id_album());
    }

}
