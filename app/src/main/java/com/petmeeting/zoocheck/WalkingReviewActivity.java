package com.petmeeting.zoocheck;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WalkingReviewActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waliking_review);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_review_dog:
                /* TODO */
                break;
            case R.id.button_review_people:
                /* TODO */
                break;
            default:
                break;
        }
    }
}
