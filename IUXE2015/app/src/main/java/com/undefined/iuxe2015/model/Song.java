package com.undefined.iuxe2015.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.undefined.iuxe2015.R;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class Song {

    private int _id;
    private int _id_stakeholder;
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

    public int get_id_stakeholder() {
        return _id_stakeholder;
    }

    public void set_id_stakeholder(int id_stakeholder) {
        this._id_stakeholder = id_stakeholder;
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
        if (name == null)
            name = "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        if (uri == null)
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
        if (artists == null)
            artists = new ArrayList<>();
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public boolean hasArtists() {
        return artists != null && artists.size() > 0;
    }

    public String getArtistsString(Context c) {
        if(artists == null || artists.size() == 0)
            return c.getString(R.string.search_unknown_artists);
        else{
            String res = artists.get(0).getName();
            for(int i = 1; i < artists.size();i++){
                res += ", " + artists.get(i).getName();
            }
            return res;
        }
    }
}
