package com.undefined.iuxe2015.model;

import com.undefined.iuxe2015.model.types.ScaleType;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class Event {

    private int _id;
    private int _id_stakeholder;
    private String name;
    private long date;

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public int get_id_stakeholder() {
        return _id_stakeholder;
    }

    public void set_id_stakeholder(int stakeholderId) {
        this._id_stakeholder = stakeholderId;
    }

    public String getName() {
        if (name == null)
            name = "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
