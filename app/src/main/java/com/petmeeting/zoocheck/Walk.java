package com.petmeeting.zoocheck;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.security.acl.Owner;
import java.time.LocalDateTime;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public LocalDateTime getWalkAt() {
        return walkAt;
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

    public void transferWalk(RetrofitService service, Callback<JsonObject> callback) {
        Call<JsonObject> callWalk = service.createWalk(this);
        callWalk.enqueue(callback);
    }
}
