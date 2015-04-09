package com.undefined.iuxe2015.model.types;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public enum RatingType {
    BINARY(2), LIKERT(7);

    //DEFINE ratings between 0 and 100, and can therefor be parsed/read as another rating.

    private int options;

    RatingType(int options) {
        this.options = options;
    }

    public int getOptions() {
        return options;
    }

    public int parseRating(int rating){
        //ratings is in <0, 100>
        //TODO: parse to rating system of  'this'
        return rating;
    }

    public static RatingType getDefault(){
        return BINARY;
    }
}
