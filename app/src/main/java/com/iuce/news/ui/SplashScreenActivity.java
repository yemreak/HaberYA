package com.iuce.news.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.iuce.news.R;


/*
 * Example: https://github.com/SabithPkcMnr/SplashScreen
 */
public class SplashScreenActivity extends AppCompatActivity {

    final int SPLASH_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);

            // remove activity from activity stack
            finish();

        }, SPLASH_TIME);
    }

}
