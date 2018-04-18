package com.example.thienpro.mvp_firebase.model.entity;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserLocation implements Serializable {
    private String name;
    private String id;
    private double lng;
    private double lat;
    private String time;

    public UserLocation() {
    }

    public UserLocation(String name, String id, double lng, double lat, String time) {
        this.name = name;
        this.id = id;
        this.lng = lng;
        this.lat = lat;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("lng", lng);
        result.put("lat", lat);
        result.put("time", time);
        result.put("name", name);
        result.put("id", id);
        return result;
    }
}
