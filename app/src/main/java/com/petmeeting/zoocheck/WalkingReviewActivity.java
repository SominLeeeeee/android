package com.petmeeting.zoocheck;

import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WalkingReviewActivity extends AppCompatActivity implements View.OnClickListener {
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://3.137.184.184/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    RetrofitService service = retrofit.create(RetrofitService.class);

    int score, activity, sociality, aggression, bark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waliking_review);

        findViewById(R.id.button_review_walking).setOnClickListener(this);
        findViewById(R.id.button_walk).setOnClickListener(this);

        ArrayList<Integer> ratingList = new ArrayList<Integer>();
        ratingList.add(R.id.rating_dog_activity);
        ratingList.add(R.id.rating_dog_aggression);
        ratingList.add(R.id.rating_dog_bark);
        ratingList.add(R.id.rating_dog_sociality);

        for(int i : ratingList) {
            ratingBarListener(i);
        }
    }

    public void ratingBarListener(int ratingBarId) {
        RatingBar rb = (RatingBar) findViewById(ratingBarId);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                switch(ratingBarId) {
                    case R.id.rating_dog_activity:
                        activity = (int)v;
                        String temp1 = "activity : " + Integer.toString(activity);
                        Toast.makeText(getApplicationContext(), temp1, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rating_dog_aggression:
                        aggression = (int) v;
                        String temp2 = "aggression : " + Integer.toString(aggression);
                        Toast.makeText(getApplicationContext(), temp2, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rating_dog_bark:
                        bark = (int) v;
                        String temp3 = "bark : " + Integer.toString(bark);
                        Toast.makeText(getApplicationContext(), temp3, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rating_dog_sociality:
                        sociality = (int) v;
                        String temp4 = "sociality : " + Integer.toString(sociality);
                        Toast.makeText(getApplicationContext(), temp4, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_review_walking:
                transferReview();
                break;
            case R.id.button_walk:
                transferWalk();
                break;
            default:
                break;
        }
    }

    /* 데이터를 입력받아 WalkingReview 형식으로 만들기 */
    protected WalkingReview makeReview() {
        String userId, ownerReview, dogReview;


        // TODO - 지금은 임시로 데이터 저장해둠, 데이터 입력받는 거 구현하기
        userId = UseSharedPref.getUserId(this);

        WalkingReview review = new WalkingReview(userId, "", "", 5, activity, sociality, aggression, bark);
        return review;
    }

    /* WalkingReview를 서버에 전송하기 */
    protected void transferReview() {
        Long walkId = UseSharedPref.getWalkId(this);
        if(walkId == null) {
            Toast.makeText(getApplicationContext(), "walkId is null", Toast.LENGTH_SHORT).show();
            return;
        }

        WalkingReview review = makeReview();
        Call<JsonObject> response = service.createReview(walkId, review);

        response.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Log.d("review response: ", "successful");
                    Toast.makeText(getApplicationContext(), "response success", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("review response: ", "fail");
                    Toast.makeText(getApplicationContext(), "response fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("network: ", "fail");
            }
        });
    }

    /* 산책 만들기 */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Walk makeWalk() {
        Walk walk = new Walk(UseSharedPref.getUserId(this),
                LocalDateTime.of(2020,12,12,20,10,10),
                127.021, 63.32432123, 30, 500);
        return walk;
    }

    /* 산책 등록 */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void transferWalk() {
        Callback<JsonObject> callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Long walkId = response.body().get("id").getAsLong();
                    Toast.makeText(getApplicationContext(), "post walk success", Toast.LENGTH_SHORT).show();
                    Log.d("get walkId", "success");
                    UseSharedPref.setWalkId(getApplicationContext(), walkId);
                } else {
                    Toast.makeText(getApplicationContext(), "post walk fail", Toast.LENGTH_SHORT).show();
                    Log.d("get walkId", "failure");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "post walk fail", Toast.LENGTH_SHORT).show();
                Log.d("network: ", "failure");
            }
        };

        Walk walk = makeWalk();
        walk.transferWalk(service, callback);
    }

    /* TODO - 응답 받아서 처리하기 */
}
