package com.petmeeting.zoocheck;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Walk {
    @SerializedName("id")
    private String id;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("expectedElapsedTime")
    private int expectedElapsedTime;
    @SerializedName("area")
    private int area;
    @SerializedName("isGroup")
    private Boolean isGroup;
    @SerializedName("minHeadCount")
    private int minHeadCount;
    @SerializedName("maxHeadCount")
    private int maxHeadCount;


    public Walk(HashMap<String, Object> param) {
        this.id = (String) param.get("id");
        this.latitude = (double) param.get("id");
        this.longitude = (double) param.get("id");
        this.expectedElapsedTime = (int) param.get("id");
        this.area = (int) param.get("id");
        this.isGroup = (Boolean) param.get("id");
        this.minHeadCount = (int) param.get("id");
        this.maxHeadCount = (int) param.get("id");
    }

    public String setId() {
        return this.id;
    }
    public double setLatitude() {
        return this.latitude;
    }
    public double setLongitude() {
        return this.longitude;
    }
    public int setExpectedElapsedTime() {
        return this.expectedElapsedTime;
    }
    public int setArea() {
        return this.area;
    }
    public Boolean setIsGroup() {
        return this.isGroup;
    }
    public int setMinHeadCount() {
        return this.minHeadCount;
    }
    public int setMaxHeadCount() {
        return this.maxHeadCount;
    }

    public String getId() {
        return id;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public int getExpectedElapsedTime() {
        return expectedElapsedTime;
    }
    public int getArea() {
        return area;
    }
    public Boolean getIsGroup() { return isGroup; }
    public int getMinHeadCount() {
        return minHeadCount;
    }
    public int getMaxHeadCount() {
        return maxHeadCount;
    }
}
