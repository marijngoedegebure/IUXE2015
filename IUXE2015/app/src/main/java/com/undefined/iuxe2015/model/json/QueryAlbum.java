package com.undefined.iuxe2015.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class QueryAlbum {

    @SerializedName("images")
    public ArrayList<QueryImage> images;

    @SerializedName("name")
    public String name;
}
