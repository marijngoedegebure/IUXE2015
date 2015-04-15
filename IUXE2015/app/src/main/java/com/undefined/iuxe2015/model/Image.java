package com.undefined.iuxe2015.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class Image {

    private int _id;
    private int _id_album;

    @SerializedName("height")
    public int height;

    @SerializedName("width")
    public int width;

    @SerializedName("url")
    public String url;

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        if (url == null)
            url = "";
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
