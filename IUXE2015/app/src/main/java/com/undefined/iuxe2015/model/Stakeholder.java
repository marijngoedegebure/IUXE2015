package com.undefined.iuxe2015.model;

import com.undefined.iuxe2015.tools.PreferenceTool;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class Stakeholder extends Actor {

    private int _id;
    private String name;
    private int age;
    private int prefFontSize;

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public String getName() {
        if (name == null)
            name = "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPrefFontSize() {
        if (prefFontSize == 0)
            prefFontSize = PreferenceTool.DEFAULT_FONTSIZE;
        return prefFontSize;
    }

    public void setPrefFontSize(int prefFontSize) {
        this.prefFontSize = prefFontSize;
    }
}
