package com.undefined.iuxe2015.model;

import com.google.gson.annotations.SerializedName;
import com.undefined.iuxe2015.model.json.QueryAlbum;
import com.undefined.iuxe2015.model.json.QueryArtist;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class Song {

    private int dbId;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("uri")
    private String uri;

    @SerializedName("duration_ms")
    private long duration_ms;

    @SerializedName("album")
    private QueryAlbum album;

    @SerializedName("artist")
    private ArrayList<QueryArtist> artist;

    //TODO remove constructor or reove with more advanced one?
    public Song(String name) {
        this.name = name;
    }

    public String getTitle() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUri() { return uri; }
}
