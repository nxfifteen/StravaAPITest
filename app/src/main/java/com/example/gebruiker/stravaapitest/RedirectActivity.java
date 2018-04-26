package com.example.gebruiker.stravaapitest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RedirectActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        Uri returnUrl = getIntent().getData();
        if (returnUrl != null) {

            NxStravaHelper strava = new NxStravaHelper();
            strava.requestAccessTokenFromIntent(returnUrl);

        } else {
            Log.d(TAG, "Something is wrong with the return value from Strava. getIntent().getData() is NULL?");
        }
    }
}
