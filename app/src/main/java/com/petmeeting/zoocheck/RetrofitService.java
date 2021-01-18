package com.petmeeting.zoocheck;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
    String URL="http://3.137.184.184/";

    @POST("/users")
    //Call<return 형식>
    Call<String> createUser (@Body Users user);

    @POST("/walks/{walkId}/reviews")
    Call<JsonObject> createReview (@Path("walkId") long walkId, @Body WalkingReview review);

    @POST("/walks")
    Call<JsonObject> createWalk (@Body Walk walk);
}
