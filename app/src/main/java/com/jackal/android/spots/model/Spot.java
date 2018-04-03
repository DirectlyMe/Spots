package com.jackal.android.spots.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jack on 3/21/18.
 */

public class Spot {

    private UUID locationID;

    private String title;

    private String description;

    private double lat;

    private double lon;

    private String imageUrl1;

    private String imageUrl2;

    private String imageUrl3;

    public Spot() { }

    public Spot(double lat, double lon, String title, String description) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
        this.description = description;
        this.locationID = UUID.randomUUID();
    }

    public String getLocationID() {
        return locationID.toString();
    }

    public void setLocationID(String locationID) {
        this.locationID = (UUID.fromString(locationID));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }


    public List<String> getPhotoFileNames() {
        List<String> fileNames = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            fileNames.add("IMG_" + getLocationID() + String.valueOf(i) + ".jpg");
        }
        return fileNames;
    }
}
