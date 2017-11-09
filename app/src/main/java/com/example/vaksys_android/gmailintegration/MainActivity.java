package com.example.vaksys_android.gmailintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener

{

    private LinearLayout profile_section;
    private SignInButton signInButton;
    private Button logout;
    private ImageView profile_pic;
    private TextView name_text,email_text;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_gmail);
        profile_section = (LinearLayout) findViewById(R.id.profile_section1);
        logout = (Button)findViewById(R.id.button_logout);
        signInButton = (SignInButton)findViewById(R.id.btn_sign_in);
        profile_pic = (ImageView)findViewById(R.id.image_view);
        name_text = (TextView)findViewById(R.id.text1);
        email_text = (TextView)findViewById(R.id.text2);
        logout.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        profile_section.setVisibility(View.GONE);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,options).build();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

            case R.id.btn_sign_in:
                signIn();
                break;

            case R.id.button_logout:
                signOut();
                break;
        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn()
    {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }

    private void signOut()
    {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void handleResult(GoogleSignInResult result)
    {

        if (result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            name_text.setText(name);
            email_text.setText(email);
            Glide.with(this).load(img_url).into(profile_pic);
            updateUI(true);

        }

        else
        {
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin)
    {
        if(isLogin)
        {
            profile_section.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
        }
        else {
            profile_section.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
