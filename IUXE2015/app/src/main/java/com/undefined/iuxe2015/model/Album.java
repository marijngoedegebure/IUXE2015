package com.undefined.iuxe2015.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class Album {

    private int _id;
    private int _id_stakeholder;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    public String name;

    @SerializedName("images")
    public ArrayList<Image> images;

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public void set_id_stakeholder(int _id_stakeholder) {
        this._id_stakeholder = _id_stakeholder;
    }

    public int get_id_stakeholder() {
        return _id_stakeholder;
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

    public ArrayList<Image> getImages() {
        if(images == null)
            images = new ArrayList<>();
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
