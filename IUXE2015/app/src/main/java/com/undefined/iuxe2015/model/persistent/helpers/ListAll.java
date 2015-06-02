package com.undefined.iuxe2015.model.persistent.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Album;
import com.undefined.iuxe2015.model.Artist;
import com.undefined.iuxe2015.model.Event;
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
    public static ArrayList<Rating> ratings(SQLiteDatabase database, int stakeholderId) {
        ArrayList<Rating> ratings = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_RATINGS, MumoDbHelper.allRatingColumns,
                    MumoDbHelper.RATINGS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId, null, null, null, MumoDbHelper.RATINGS_COLUMN_RATING);

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

    public static Rating ratingsForSong(SQLiteDatabase database, int stakeholderId, Song song) {
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_RATINGS, MumoDbHelper.allRatingColumns,
                    MumoDbHelper.RATINGS_COLUMN_ID_SONG + " = " + song.get_id() + " AND " +
                            MumoDbHelper.RATINGS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId,
                    null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
                return CursorTo.rating(cursor, true);
            // make sure to close the cursor
            cursor.close();
        }
        return null;
    }

    public static ArrayList<Song> songs(SQLiteDatabase database, int stakeholderId) {
        ArrayList<Song> songs = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_SONGS, MumoDbHelper.allSongColumns,
                    MumoDbHelper.SONGS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                songs.add(CursorTo.song(cursor, false));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }

        for (Song s : songs) {
            s.setAlbum(albumForSong(database, stakeholderId, s));
            s.setArtists(artistsForSong(database, stakeholderId, s));
        }

        return songs;
    }

    public static Song getSong(SQLiteDatabase database, int stakeholderId, int songId) {
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_SONGS, MumoDbHelper.allSongColumns,
                    MumoDbHelper.SONGS_COLUMN_ID + " = " + songId + " AND " +
                            MumoDbHelper.SONGS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId,
                    null, null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast())
                return CursorTo.song(cursor, true);
        }
        return null;
    }

    public static ArrayList<Song> getSongsByEvent(SQLiteDatabase database, int stakeholderId, int eventId) {
        ArrayList<Song> songs = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_RATINGS, MumoDbHelper.allRatingColumns,
                    MumoDbHelper.RATINGS_COLUMN_ID_EVENT + " = " + eventId + " AND " +
                            MumoDbHelper.RATINGS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId,
                    null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Rating r = CursorTo.rating(cursor, false);
                songs.add(ListAll.getSong(database, stakeholderId, r.get_id_song()));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }

        return songs;
    }

    public static Song getSongById(SQLiteDatabase database, int stakeholderId, String id) {
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_SONGS, MumoDbHelper.allSongColumns,
                    MumoDbHelper.SONGS_COLUMN_ID_SONG + " ='" + id + "'" + " AND " +
                            MumoDbHelper.SONGS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId,
                    null, null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast())
                return CursorTo.song(cursor, true);
        }
        return null;
    }

    public static ArrayList<Artist> artistsForSong(SQLiteDatabase database, int stakeholderId, Song song) {
        ArrayList<Artist> artists = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_ARTISTS, MumoDbHelper.allArtistColumns,
                    MumoDbHelper.ARTISTS_COLUMN_ID_SONG + " = " + song.get_id() + " AND " +
                            MumoDbHelper.ARTISTS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId,
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

    public static Album albumForSong(SQLiteDatabase database, int stakeholderId, Song song) {
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_ALBUMS, MumoDbHelper.allAlbumColumns,
                    MumoDbHelper.ALBUMS_COLUMN_ID + " = " + song.get_id_album() + " AND " +
                            MumoDbHelper.ALBUMS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId,
                    null, null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                Album album = CursorTo.album(cursor, true);
                album.setImages(ListAll.imageForAlbum(database, stakeholderId, album));
                return album;
            }
        }
        return null;
    }

    public static ArrayList<Image> imageForAlbum(SQLiteDatabase database, int stakeholderId, Album album) {
        ArrayList<Image> images = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_IMAGES, MumoDbHelper.allImageColumns,
                    MumoDbHelper.IMAGES_COLUMN_ID_ALBUM + " = " + album.get_id() + " AND " +
                            MumoDbHelper.IMAGES_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId,
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

    public static Stakeholder stakeholder(SQLiteDatabase database, int stakeholderId) {
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_STAKEHOLDERS, MumoDbHelper.allStakeholderColumns,
                    MumoDbHelper.STAKEHOLDERS_COLUMN_ID + " = " + stakeholderId,
                    null, null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast())
                return CursorTo.stakeholder(cursor, true);
        }
        return null;
    }

    public static ArrayList<Event> events(SQLiteDatabase database, int stakeholderId) {
        ArrayList<Event> events = new ArrayList<>();
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_EVENTS, MumoDbHelper.allEventColumns,
                    MumoDbHelper.EVENTS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                events.add(CursorTo.event(cursor, false));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return events;
    }

    public static Event event(SQLiteDatabase database, int stakeholderId, int eventId) {
        if (database.isOpen()) {
            Cursor cursor = database.query(MumoDbHelper.TABLE_EVENTS, MumoDbHelper.allEventColumns,
                    MumoDbHelper.EVENTS_COLUMN_ID + " = " + eventId + " AND " +
                            MumoDbHelper.EVENTS_COLUMN_ID_STAKEHOLDER + " = " + stakeholderId,
                    null, null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast())
                return CursorTo.event(cursor, true);
        }
        return null;
    }
}
