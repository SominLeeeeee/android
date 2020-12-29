package com.petmeeting.zoocheck;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    String URL="http://3.137.184.184/";

    @POST("/users")
    //Call<return 형식>
    Call<String> createUser (@Body Users user);
}
