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

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "Oauth2Google";

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account == null) {
            Log.d("account is ", "null");
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://3.137.184.184/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<String> response = service.createUser(new Users(account.getId(), account.getEmail(), account.getDisplayName()));

            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()) {
                        // 가입을 안 했던 회원이라면
                        Log.d("success:", "가입 안 했던 회원");
                    } else {
                        String jsonStr = response.errorBody().toString();
                        Log.d("fail:", "중복 회원");
                        /* 응답받은 것을 json으로 바꾸는 방법
                        Gson gson = new Gson();
                        JsonElement element = gson.fromJson(jsonStr, JsonElement.class);
                        JsonObject jsonObject = element.getAsJsonObject(); */
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("network ", "failure");
                }
            });
            Log.d("d","D");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}