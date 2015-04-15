package com.undefined.iuxe2015.model.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class QueryResult {
    @SerializedName("tracks")
    public QueryTracks tracks;

    public boolean hasTracks(){
        return tracks !=null && tracks.hasSongs();
    }
}
