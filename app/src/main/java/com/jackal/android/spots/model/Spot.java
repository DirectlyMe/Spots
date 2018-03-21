package com.jackal.android.spots.model;

/**
 * Created by Jack on 3/21/18.
 */

public class Spot {

    private String mTitle;

    private String mDescription;

    private double mLat;

    private double mLon;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }


    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }


    public double getLon() {
        return mLon;
    }

    public void setLon(double lon) {
        mLon = lon;
    }
}
