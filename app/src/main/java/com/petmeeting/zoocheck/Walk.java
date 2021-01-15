package com.petmeeting.zoocheck;

import java.security.acl.Owner;
import java.time.LocalDateTime;
import java.util.Date;

// api 문서 : https://github.com/ZooCheck/backend/blob/main/document/Walk.md

public class Walk {

    Users user;
    LocalDateTime walkAt;
    double latitude, longitude;
    int expectedElapsedTime; // min
    int area;

    public Walk(String ownerId, LocalDateTime walkAt, double latitude, double longitude, int time, int area) {
        this.user = new Users(ownerId, "", "");
        this.walkAt = walkAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.expectedElapsedTime = time;
        this.area = area;
    }

    public Users getUser() {
        return user;
    }

    public void setOwner(Users user) {
        this.user = user;
    }

    public LocalDateTime getWalkAt() {
        return walkAt;
    }

    public void setWalkAt(LocalDateTime walkAt) {
        this.walkAt = walkAt;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getExpectedElapsedTime() {
        return expectedElapsedTime;
    }

    public void setExpectedElapsedTime(int expectedElapsedTime) {
        this.expectedElapsedTime = expectedElapsedTime;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

}
