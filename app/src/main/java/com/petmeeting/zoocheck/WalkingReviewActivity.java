package com.petmeeting.zoocheck;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WalkingReviewActivity extends AppCompatActivity implements View.OnClickListener {
    Long walkId;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://3.137.184.184/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    RetrofitService service = retrofit.create(RetrofitService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waliking_review);

        findViewById(R.id.button_review_dog).setOnClickListener(this);
        findViewById(R.id.button_review_people).setOnClickListener(this);
        findViewById(R.id.button_walk).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_review_dog:
                transferReview();
                break;
            case R.id.button_review_people:
                /* TODO */
                break;
            case R.id.button_walk:
                transferWalk();
                break;
            default:
                break;
        }
    }

    /* sharedPreference로부터 userId 가져오기 */
    protected String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");
        return userId;
    }

    /* 데이터를 입력받아 WalkingReview 형식으로 만들기 */
    protected WalkingReview makeReview(long walkId) {
        String userId, message = "test";
        int score = 3;

        // TODO - 지금은 임시로 데이터 저장해둠, 데이터 입력받는 거 구현하기
        userId = getUserId();

        WalkingReview review = new WalkingReview(userId, message, score, walkId);
        return review;
    }

    /* WalkingReview를 서버에 전송하기 */
    protected void transferReview() {
        long walkId = (long) (Math.random() * 10000);
        WalkingReview review = makeReview(walkId);
        Call<JSONObject> response = service.createReview(walkId, review);

        response.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
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
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d("network: ", "fail");
            }
        });
    }

    /* 산책 만들기 */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Walk makeWalk() {
        Walk walk = new Walk(getUserId(),
                LocalDateTime.of(2020,12,12,20,10,10),
                127.021, 63.32432123, 30, 500);
        return walk;
    }

    /* 산책 등록 */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void transferWalk() {
        Walk walk = makeWalk();
        Call<JsonObject> callWalk = service.createWalk(walk);

        callWalk.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    /* TODO - response에서 walkId 뽑아내서 사용하기 */
                    Toast.makeText(getApplicationContext(), "get walkid success", Toast.LENGTH_SHORT).show();
                    walkId = response.body().get("id").getAsLong();
                    Log.d("walkId : ", walkId.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "fail!!!!", Toast.LENGTH_SHORT).show();
                }
                Log.d("d", "d");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "network failure", Toast.LENGTH_SHORT).show();
                Log.d("network: ", "fail");
            }
        });
    }

    /* TODO - 응답 받아서 처리하기 */
}
