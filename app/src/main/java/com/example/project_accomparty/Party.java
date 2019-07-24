package com.example.project_accomparty;

import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class Party {
    // coordinates
    private String lat, lng;
    private String name, host, address, type, description;
    // Boolean for split cost and double for cost
    private boolean split;
    private String cost;

    public Party(){

    }
    // constructor when split not requested
    public Party(String name, String host, String address, String type, String description) {
        this.name = name;
        this.host = host;
        this.address = address;
        this.type = type;
        this.description = description;
        this.split = false;
        this.cost = "0.0";
    }

    // constructor when split is requested
    public Party(String name, String host, String address, String type, String description, String cost) {
        this.name = name;
        this.host = host;
        this.address = address;
        this.type = type;
        this.description = description;
        this.split = true;
        this.cost = cost;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSplit() {
        return split;
    }

    public String getCost() {
        return cost;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCost(String cost){
        if(!this.split)
            this.split = true;
        this.cost = cost;
    }

    public void setLat(String lat){
        this.lat = lat;
    }

    public void setLng(String lng){
        this.lng = lng;
    }
}
