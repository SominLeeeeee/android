package com.petmeeting.zoocheck;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.getClient;
import static com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent;

public class SignInGoogle implements SignIn {
    GoogleSignInClient mGoogleSignInClient;
    Activity activity;
    private static final int GOOGLE_SIGN_IN = 9001;
    private static final String TAG = "Oauth2Google";

    protected void initGoogleClient(Activity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = getClient(activity, gso);
    }

    @Override
    public void signIn(Activity activity) {
        initGoogleClient(activity);

        Intent intent = new Intent();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
        /* LoginActivity에서 startActivityForResult를 실행했으므로
        * 결과값을 반환받으면 LoginActivity의 onActivityResult가 실행됨 */
    }

    @Override
    public Users parse(Intent data) {
        Task<GoogleSignInAccount> task = getSignedInAccountFromIntent(data);
        GoogleSignInAccount account = null;

        // handle sign in result
        try { // signed in successfully
            account = task.getResult(ApiException.class);
            return new Users(account.getEmail(), account.getEmail(), account.getDisplayName());
        } catch(ApiException e) { // log - failure reason
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            return null;
        }
    }
}
