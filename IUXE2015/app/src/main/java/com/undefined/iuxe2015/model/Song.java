package com.undefined.iuxe2015.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class Song {

    private int _id;
    private int _id_album;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("uri")
    private String uri;

    @SerializedName("duration_ms")
    private long duration_ms;

    @SerializedName("album")
    private Album album;

    @SerializedName("artists")
    private ArrayList<Artist> artists;

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public int get_id_album() {
        return _id_album;
    }

    public void set_id_album(int albumId) {
        this._id_album = albumId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if(name == null)
            name = "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        if(uri == null)
            uri = "";
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getDurationMs() {
        return duration_ms;
    }

    public void setDurationMs(long durationMs) {
        this.duration_ms = durationMs;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public ArrayList<Artist> getArtists() {
        if(artists == null)
            artists = new ArrayList<>();
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }
}
