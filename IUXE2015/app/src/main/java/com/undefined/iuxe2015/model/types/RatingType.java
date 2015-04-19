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

    public int parseRating(int rating) {
        //ratings is in <0, 100>

        double rationfraction = rating / 101.0;

        return (int) Math.round(
                Math.round(rationfraction * (getOptions() - 1)) * 101.0
                        / (getOptions() - 1));
//
//        switch (this) {
//            case BINARY:
//                return rating > 50 ? 100 : 1;
//            case LIKERT:
//                return rating; //TODO: parse to rating system of  'likert'
//            default:
//                return rating;
//        }
    }

    public static RatingType getDefault() {
        return BINARY;
    }
}
