package com.jackal.android.spots.model;

import java.util.List;
import java.util.UUID;

/**
 * Created by Jack on 3/21/18.
 */

public class Spot {

    private UUID location_id;

    private String title;

    private String description;

    private double lat;

    private double lon;

    private List<String> imageUrls;

    public Spot(double lat, double lon, String title, String description) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
        this.description = description;
        location_id = UUID.randomUUID();
    }

    public UUID getLocation_id() {
        return location_id;
    }

    public void setLocation_id(UUID location_id) {
        this.location_id = location_id;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getPhotoFileName() {
        return "IMG_" + getLocation_id() + ".jpg";
    }
}
