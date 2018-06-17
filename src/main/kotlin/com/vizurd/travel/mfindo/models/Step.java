package com.vizurd.travel.mfindo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("distance")
    @Expose
    private Object distance;
    @SerializedName("duration")
    @Expose
    private Object duration;
    @SerializedName("end_location")
    @Expose
    private Object endLocation;
    @SerializedName("start_location")
    @Expose
    private Object startLocation;

    public Object getDistance() {
        return distance;
    }

    public void setDistance(Object distance) {
        this.distance = distance;
    }

    public Object getDuration() {
        return duration;
    }

    public void setDuration(Object duration) {
        this.duration = duration;
    }

    public Object getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Object endLocation) {
        this.endLocation = endLocation;
    }

    public Object getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Object startLocation) {
        this.startLocation = startLocation;
    }

}
