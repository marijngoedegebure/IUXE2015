package com.undefined.iuxe2015.model;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class Rating {
    private int _id;
    private int _id_song;
    private int rating;
    private String note;
    private long timestamp;

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public int get_id_song() {
        return _id_song;
    }

    public void set_id_song(int songId) {
        this._id_song = songId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNote() {
        if (note == null)
            note = "";
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
