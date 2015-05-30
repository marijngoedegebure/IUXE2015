package com.undefined.iuxe2015.model;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class Event {

    private int _id;
    private int _id_stakeholder;
    private String name;
    private int year;

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

    public long getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
