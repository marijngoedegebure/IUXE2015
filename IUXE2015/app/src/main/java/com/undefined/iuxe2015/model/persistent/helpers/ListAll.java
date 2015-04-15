package com.undefined.iuxe2015.model.persistent.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Album;
import com.undefined.iuxe2015.model.Artist;
import com.undefined.iuxe2015.model.Image;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.MumoDbHelper;

import java.util.ArrayList;

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

    public static ArrayList<Song> songs(SQLiteDatabase database) {
        ArrayList<Song> songs = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_SONGS, MumoDbHelper.allSongColumns,
                    null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                songs.add(CursorTo.song(cursor, false));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }

        for (Song s : songs) {
            s.setAlbum(albumForSong(database, s));
            s.setArtists(artistsForSong(database, s));
        }

        return songs;
    }

    public static ArrayList<Artist> artistsForSong(SQLiteDatabase database, Song song) {
        ArrayList<Artist> artists = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_ARTISTS, MumoDbHelper.allArtistColumns,
                    MumoDbHelper.ARTISTS_COLUMN_ID_SONG + " = " + song.get_id(),
                    null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                artists.add(CursorTo.artist(cursor, false));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return artists;
    }

    public static Album albumForSong(SQLiteDatabase database, Song song) {
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_ALBUMS, MumoDbHelper.allAlbumColumns,
                    MumoDbHelper.ALBUMS_COLUMN_ID + " = " + song.get_id_album(),
                    null, null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                Album album = CursorTo.album(cursor, true);
                album.setImages(ListAll.imageForAlbum(database, album));
                return album;
            }
        }
        return null;
    }

    public static ArrayList<Image> imageForAlbum(SQLiteDatabase database, Album album) {
        ArrayList<Image> images = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_IMAGES, MumoDbHelper.allImageColumns,
                    MumoDbHelper.IMAGES_COLUMN_ID_ALBUM + " = " + album.get_id(),
                    null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                images.add(CursorTo.image(cursor, false));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return images;
    }

    public static ArrayList<Stakeholder> stakeholders(SQLiteDatabase database) {
        ArrayList<Stakeholder> stakeholders = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_STAKEHOLDERS, MumoDbHelper.allStakeholderColumns,
                    null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                stakeholders.add(CursorTo.stakeholder(cursor, false));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return stakeholders;
    }
}
