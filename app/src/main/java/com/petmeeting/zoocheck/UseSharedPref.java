package com.petmeeting.zoocheck;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class UseSharedPref extends AppCompatActivity {

    protected static void setUserId(Context context, String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("account", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", userId);
        editor.commit();
    }

    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("account", MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");
        return userId;
    }

    protected static void setWalkId(Context context, Long walkId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("walk", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("id", walkId);
        editor.commit();
    }

    public static Long getWalkId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("walk", MODE_PRIVATE);
        Long walkId = sharedPreferences.getLong("id", 0);
        return walkId;
    }
}
