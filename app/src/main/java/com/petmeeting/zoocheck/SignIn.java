package com.petmeeting.zoocheck;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.tasks.Task;

public interface SignIn {

    /* 로그인 버튼을 눌렀을 때 부터 intent data를 받아올 때까지 */
    public void signIn(Activity activity);

    /* intent data를 받고 Users 객체를 만들어 반환 */
    public Users parse(Intent data);
}
