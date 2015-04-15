package com.undefined.iuxe2015.model.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class QueryImage {

    private int id;

    @SerializedName("height")
    public int height;

    @SerializedName("width")
    public int width;

    @SerializedName("url")
    public String url;
}
