package com.example.to_do.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.to_do.R;

public class SplashActivity extends AppCompatActivity {

    public static String OPENED = "opened";
    private boolean open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences(SplashActivity.OPENED, MODE_PRIVATE);
        open = sharedPreferences.getBoolean(SplashActivity.OPENED, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!open) startActivity(new Intent(getBaseContext(), PagerActivity.class));
                else startActivity(new Intent(getBaseContext(), HomeActivity.class));
                finish();
            }
        }, 3000);
    }
}