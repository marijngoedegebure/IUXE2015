package com.undefined.iuxe2015.model;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class Song {
    private String id;
    private String genre;
    private String title;
    private String artist;
    private String uri;
    private int duration;
    private int name;

    //TODO remove constructor or reove with more advanced one?
    public Song(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
