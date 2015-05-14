package com.undefined.iuxe2015.model;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class Rating {
    private int _id;
    private int _id_stakeholder;
    private int _id_song;
    private int _id_event;
    private int rating;
    private String note;
    private long timestamp;

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public int get_id_stakeholder() {
        return _id_stakeholder;
    }

    public void set_id_stakeholder(int id_stakeholder) {
        this._id_stakeholder = id_stakeholder;
    }

    public int get_id_song() {
        return _id_song;
    }

    public void set_id_song(int songId) {
        this._id_song = songId;
    }

    public int get_id_event() {
        return _id_event;
    }

    public void set_id_event(int eventId) {
        this._id_event = eventId;
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
