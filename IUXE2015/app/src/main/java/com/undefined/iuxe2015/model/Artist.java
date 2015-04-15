package com.undefined.iuxe2015.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class Artist {

    private int _id;
    private int _id_song;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    public String name;

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
}
