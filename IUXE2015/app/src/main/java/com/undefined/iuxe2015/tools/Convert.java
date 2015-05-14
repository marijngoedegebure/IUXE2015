package com.undefined.iuxe2015.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jan-Willem on 15-5-2015.
 */
public class Convert {

    private static int second = 1000;
    private static int minute = 60 * second;
    private static int hour = 60 * minute;

    public static String durationToString(long duration) {
        long minutes = duration / minute;
        duration %= minute;
        long seconds = duration / second;

        return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;

//        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
//        return s.format(new Date(duration));
    }
}
