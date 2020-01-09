package com.iuce.news.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.iuce.news.R;


/*
 * Example: https://github.com/SabithPkcMnr/SplashScreen
 */
public class SplashScreenActivity extends AppCompatActivity {

    final int SPLASH_TIME = 1500;
    ProgressBar splashProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);

        splashProgress = findViewById(R.id.splashProgress);
        playProgress();

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);

            // remove activity from activity stack
            finish();

        }, SPLASH_TIME);
    }

    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(SPLASH_TIME)
                .start();
    }

}
