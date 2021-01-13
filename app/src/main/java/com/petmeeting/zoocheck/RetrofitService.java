package com.petmeeting.zoocheck;

<<<<<<< Updated upstream
=======
import com.google.gson.JsonObject;

import org.json.JSONObject;

>>>>>>> Stashed changes
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    String URL="http://3.137.184.184/";

    @POST("/users")
    //Call<return 형식>
    Call<String> createUser (@Body Users user);
<<<<<<< Updated upstream
=======

    @POST("/walks/{walkId}/reviews")
    Call<JSONObject> createReview (@Path("walkId") long walkId, @Body WalkingReview review);

    @POST("/walks")
    Call<JsonObject> createWalk (@Body Walk walk);
>>>>>>> Stashed changes
}
