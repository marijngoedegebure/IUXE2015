package com.undefined.iuxe2015.model.json;

import com.google.gson.annotations.SerializedName;
import com.undefined.iuxe2015.model.Song;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class QueryTracks {
    @SerializedName("href")
    public String href;
    @SerializedName("items")
    public ArrayList<Song> items;


}
