package com.undefined.iuxe2015.model.types;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public enum ScaleType {
    BIG, COMPACT;

    ScaleType(){

    }

    public static ScaleType getDefault(){
        return BIG;
    }
}
