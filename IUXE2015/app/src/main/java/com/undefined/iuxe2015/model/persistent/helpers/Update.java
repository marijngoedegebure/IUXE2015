package com.undefined.iuxe2015.model.persistent.helpers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.undefined.iuxe2015.model.Event;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.MumoDbHelper;

/**
 * Created by Jan-Willem on 22-2-2015.
 */
public class Update {
    public static Rating rating(SQLiteDatabase database, int stakeholderId, int eventId, Rating rating) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.RATINGS_COLUMN_ID_STAKEHOLDER, stakeholderId);
        values.put(MumoDbHelper.RATINGS_COLUMN_ID_SONG, rating.get_id_song());
        values.put(MumoDbHelper.RATINGS_COLUMN_ID_EVENT, eventId);
        values.put(MumoDbHelper.RATINGS_COLUMN_RATING, rating.getRating());
        values.put(MumoDbHelper.RATINGS_COLUMN_NOTE, rating.getNote());
        values.put(MumoDbHelper.RATINGS_COLUMN_TIMESTAMP, System.currentTimeMillis());

        database.update(MumoDbHelper.TABLE_RATINGS, values,
                MumoDbHelper.RATINGS_COLUMN_ID + " = " + rating.get_id(),
                null);

        return rating;
    }

    public static Stakeholder stakeholder(SQLiteDatabase database, Stakeholder stakeholder) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.STAKEHOLDERS_COLUMN_NAME, stakeholder.getName());
        values.put(MumoDbHelper.STAKEHOLDERS_COLUMN_AGE, stakeholder.getAge());
        values.put(MumoDbHelper.STAKEHOLDERS_COLUMN_UI_SCALE, stakeholder.getScaleType().name());

        database.update(MumoDbHelper.TABLE_STAKEHOLDERS, values,
                MumoDbHelper.STAKEHOLDERS_COLUMN_ID + " = " + stakeholder.get_id(),
                null);

        return stakeholder;
    }

    public static Event event(SQLiteDatabase database, int stakeholderId, Event event) {
        ContentValues values = new ContentValues();
        values.put(MumoDbHelper.EVENTS_COLUMN_ID_STAKEHOLDER, stakeholderId);
        values.put(MumoDbHelper.EVENTS_COLUMN_NAME, event.getName());
        values.put(MumoDbHelper.EVENTS_COLUMN_DATE, event.getDate());

        database.update(MumoDbHelper.TABLE_EVENTS, values,
                MumoDbHelper.EVENTS_COLUMN_ID + " = " + event.get_id(),
                null);

        return event;
    }
}
