package com.petmeeting.zoocheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WalkingReviewActivity extends AppCompatActivity implements View.OnClickListener {
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

    /* TODO - WalkingReview를 서버에 전송하기 */

    /* TODO - 응답 받아서 처리하기 */
}
