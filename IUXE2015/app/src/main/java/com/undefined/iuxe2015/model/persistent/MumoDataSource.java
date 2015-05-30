package com.undefined.iuxe2015.model.persistent;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Album;
import com.undefined.iuxe2015.model.Artist;
import com.undefined.iuxe2015.model.Event;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.helpers.Create;
import com.undefined.iuxe2015.model.persistent.helpers.Delete;
import com.undefined.iuxe2015.model.persistent.helpers.ListAll;
import com.undefined.iuxe2015.model.persistent.helpers.Update;
import com.undefined.iuxe2015.tools.PreferenceTool;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class MumoDataSource {
    private SQLiteDatabase database;
    private MumoDbHelper dbHelper;

    public MumoDataSource(Context context) {
        dbHelper = new MumoDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Rating> getRatings(Context c) {
        return ListAll.ratings(database, PreferenceTool.getCurrentStakeholderId(c));
    }

    public Rating getRatingsForSong(Context c, Song song) {
        return ListAll.ratingsForSong(database, PreferenceTool.getCurrentStakeholderId(c), song);
    }

    public Rating addRating(int stakeholderId, int songId, int eventId, int rating, String note) {
        return Create.rating(database, stakeholderId, songId, eventId, rating, note);
    }

    public void updateRating(int stakeholderId, int eventId, Rating rating) {
        Update.rating(database, stakeholderId, eventId, rating);
    }

    public boolean removeRating(Rating rating) {
        return Delete.rating(database, rating) > 0;
    }

    public ArrayList<Song> getRatedSongs(Context c) {
        ArrayList<Song> songs = new ArrayList<>();
        ArrayList<Rating> ratings = getRatings(c);
        for(Rating r : ratings) {
            Song song = getSong(c, r.get_id_song());
            songs.add(song);
        }
        return songs;
    }

    public Song getSong(Context c, int songId) {
        Song song = ListAll.getSong(database, PreferenceTool.getCurrentStakeholderId(c), songId);
        if (song != null) {
            song.setAlbum(ListAll.albumForSong(database, PreferenceTool.getCurrentStakeholderId(c), song));
            song.setArtists(ListAll.artistsForSong(database, PreferenceTool.getCurrentStakeholderId(c), song));
        }
        return song;
    }

    public Song getSongById(Context c, String id) {
        Song song = ListAll.getSongById(database, PreferenceTool.getCurrentStakeholderId(c), id);
        if (song != null) {
            song.setAlbum(ListAll.albumForSong(database, PreferenceTool.getCurrentStakeholderId(c), song));
            song.setArtists(ListAll.artistsForSong(database, PreferenceTool.getCurrentStakeholderId(c), song));
        }
        return song;
    }

    public ArrayList<Song> getSongsByEvent(Context c, int eventId) {
        ArrayList<Song> songs = ListAll.getSongsByEvent(database, PreferenceTool.getCurrentStakeholderId(c), eventId);
        for (Song song : songs) {
            if (song != null) {
                song.setAlbum(ListAll.albumForSong(database, PreferenceTool.getCurrentStakeholderId(c), song));
                song.setArtists(ListAll.artistsForSong(database, PreferenceTool.getCurrentStakeholderId(c), song));
            }
        }
        return songs;
    }

    public Song addSong(int stakeholderId, Song song) {
        Album album = Create.album(database, stakeholderId, song.getAlbum());
        song.set_id_album(album.get_id());
        Song saved_song = Create.song(database, stakeholderId, song);
        saved_song.setAlbum(album);
        ArrayList<Artist> saved_artist = new ArrayList<>();
        for(Artist artist : song.getArtists()) {
            artist.set_id_song(saved_song.get_id());
            saved_artist.add(Create.artist(database, stakeholderId, artist));
        }
        saved_song.setArtists(saved_artist);
        return saved_song;
    }

    public ArrayList<Stakeholder> getStakeholders() {
        return ListAll.stakeholders(database);
    }


    public Stakeholder getStakeholderWithId(int stakeholderId) {
        return ListAll.stakeholder(database, stakeholderId);
    }

    public Stakeholder updateStakeholder(Stakeholder stakeholder) {
        return Update.stakeholder(database, stakeholder);
    }

    public Stakeholder newStakeholder(String name, int age) {
        return Create.stakeholder(database, name, age);
    }

    public int deleteStakeholder(Stakeholder stakeholder) {
        return Delete.stakeholder(database, stakeholder);
    }

    public ArrayList<Event> getEvents(Context c) {
        return ListAll.events(database, PreferenceTool.getCurrentStakeholderId(c));
    }


    public Event getEventWithId(Context c, int eventId) {
        return ListAll.event(database, PreferenceTool.getCurrentStakeholderId(c), eventId);
    }

    public Event updateEvent(Context c, Event event) {
        return Update.event(database, PreferenceTool.getCurrentStakeholderId(c), event);
    }

    public Event newEvent(Context c, String name, int year) {
        return Create.event(database, PreferenceTool.getCurrentStakeholderId(c), name, year);
    }

    public int deleteEvent(Event event) {
        return Delete.event(database, event);
    }
}
