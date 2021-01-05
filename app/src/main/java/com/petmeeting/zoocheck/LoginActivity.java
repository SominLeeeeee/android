package com.petmeeting.zoocheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.petmeeting.zoocheck.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.*;

<<<<<<< Updated upstream
public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{
=======
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
>>>>>>> Stashed changes

    private static final int GOOGLE_SIGN_IN = 9001;
    private static final String TAG = "Oauth2Google";

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.sign_in_google).setOnClickListener(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        //GoogleSignInAccount account = getLastSignedInAccount(this);
        //checkBeforeSignIn(account);
        /* TODO - 자동로그인 기능 구현하기 */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SignIn user = null;

        switch(requestCode) {
            case GOOGLE_SIGN_IN:
                user = new SignInGoogle();
                break;
            default:
                break;
        }

        /* user account data를 이용하여 파싱 */
        Users account = user.parse(data);
        checkBeforeSignIn(account);
    }

    private void gotoMainActivity() {
        /* TODO - goto main */
    }

    /* 서버와 통신해서 이미 가입된 회원인지 확인하고 로그인 */
    private void checkBeforeSignIn(Users account) {
        if(account == null) {
            Log.d("account is ", "null");
            return;
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://3.137.184.184/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<String> response = service.createUser(account);

            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()) { // 가입을 안 했던 회원
                        Log.d("AlreadySignUp:", "False");
                    } else { // 가입이 되어있던 회원
                        Log.d("AlreadySignUp:", "True");
                    }
                    gotoMainActivity();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("network ", "failure");
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_google:
                SignInGoogle google = new SignInGoogle();
                google.signIn(this);
                break;
        }
    }

}