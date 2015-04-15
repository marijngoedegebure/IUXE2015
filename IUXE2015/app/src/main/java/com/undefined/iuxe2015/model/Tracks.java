package com.undefined.iuxe2015.model;

import com.google.gson.annotations.SerializedName;
import com.undefined.iuxe2015.model.Song;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class Tracks {
    @SerializedName("href")
    public String href;
    @SerializedName("items")
    public ArrayList<Song> items;

    public boolean hasSongs() {
        return items != null && items.size() > 0;
    }
}
