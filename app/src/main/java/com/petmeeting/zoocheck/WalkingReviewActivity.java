package com.petmeeting.zoocheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< Updated upstream
public class WalkingReviewActivity extends AppCompatActivity implements View.OnClickListener {
=======
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

>>>>>>> Stashed changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waliking_review);

        findViewById(R.id.button_review_dog).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_review_dog:
                // 정보 가져오는 방법
                SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
                String userId = sharedPreferences.getString("id", "");
                String temp = "불러왔습니다\nuser id : " + userId;
                Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
                /* TODO */
                break;
            case R.id.button_review_people:
                /* TODO */
                break;
            default:
                break;
        }
    }

    /* TODO - data를 입력받기 */

    /* TODO - 입력받은 데이터를 WalkingReview 형식으로 만들기 */

<<<<<<< Updated upstream
    /* TODO - WalkingReview를 서버에 전송하기 */
=======
            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d("network: ", "fail");
            }
        });
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
                } else {
                    Toast.makeText(getApplicationContext(), "fail!!!!", Toast.LENGTH_SHORT).show();
                }
                Log.d("d", "d");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "network failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
>>>>>>> Stashed changes

    /* TODO - 응답 받아서 처리하기 */
}
