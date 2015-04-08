package com.undefined.iuxe2015.model;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class Rating {
    private int id;
    private String songId;
    private int rating;
    private String note;

    public void setId(int id) {
        this.id = id;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getSongId() {
        return songId;
    }

    public int getRating() {
        return rating;
    }

    public String getNote() {
        return note;
    }
}
