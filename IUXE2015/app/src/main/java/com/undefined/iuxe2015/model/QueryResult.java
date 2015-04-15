package com.undefined.iuxe2015.model;

import com.google.gson.annotations.SerializedName;
import com.undefined.iuxe2015.model.Tracks;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class QueryResult {
    @SerializedName("tracks")
    public Tracks tracks;

    public boolean hasTracks(){
        return tracks !=null && tracks.hasSongs();
    }
}
