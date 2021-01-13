package com.petmeeting.zoocheck;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitServiceForWalk {

    @FormUrlEncoded
    @POST("walks")
    Call<Walk> createPost(@FieldMap HashMap<String, Object> param);
             /*@Field("id") String id,
            @Field("latitude") double latitude,
            @Field("longitude") double longitude,
            @Field("expectedElapsedTime") int expectedElapsedTime,
            @Field("area") int area,
            @Field("isGroup") Boolean isGroup,
            @Field("minHeadCount") int minHeadCount,
            @Field("maxHeadCount") int maxHeadCount
    );*/
}
