package com.undefined.iuxe2015.model.persistent;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.helpers.Create;
import com.undefined.iuxe2015.model.persistent.helpers.Delete;
import com.undefined.iuxe2015.model.persistent.helpers.ListAll;
import com.undefined.iuxe2015.model.persistent.helpers.Update;

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

    public ArrayList<Rating> getRatings() {
        return ListAll.ratings(database);
    }

    public ArrayList<Rating> getRatingsForSong(Song song) {
        return ListAll.ratingsForSong(database, song);
    }

    public Rating addRating(int stakeholderId, String songId, int rating, String note) {
        return Create.rating(database, stakeholderId, songId, rating, note);
    }

    public void updateRating(int stakeholderId, Rating rating) {
        Update.rating(database, stakeholderId, rating);
    }

    public boolean removeRating(Rating rating) {
        return Delete.rating(database, rating) > 0;
    }


    public ArrayList<Song> getRatedSongs() {
        ArrayList<Song> songs = new ArrayList<>();

        for(Rating r : getRatings()) {
            songs.add(getSong(r.get_id_song()));
        }
        return songs;
    }

    public Song getSong(int songId) {
        return ListAll.getSong(database, songId);
    }

    public Song getSongById(String id) {
        return ListAll.getSongById(database, id);
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
}
