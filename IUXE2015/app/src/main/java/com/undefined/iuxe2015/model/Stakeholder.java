package com.undefined.iuxe2015.model;

import android.util.Log;

import com.undefined.iuxe2015.model.types.ScaleType;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class Stakeholder extends Actor {

    private int _id;
    private String name;
    private int age;
    private ScaleType scaleType;

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

    public ScaleType getScaleType() {
        if (scaleType == null)
            scaleType = ScaleType.getDefault();
        return scaleType;
    }

    public void setScaleType(String scaleType) {
        try{
            setScaleType(ScaleType.valueOf(scaleType));
        }catch(IllegalArgumentException e){
            Log.w("Stakeholder", "setScaleType Warning: " + e.getLocalizedMessage());
            setScaleType(ScaleType.getDefault());
        }
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }
}
