package com.iuce.news.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.iuce.news.R;


/*
* Example: https://github.com/SabithPkcMnr/SplashScreen
*/
public class SpalshScreenActivity extends AppCompatActivity {

    ProgressBar splashProgress;
    int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        splashProgress = findViewById(R.id.splashProgress);
        playProgress();

        new Handler().postDelayed(()-> {
                Intent mySuperIntent = new Intent(SpalshScreenActivity.this, MainActivity.class);
                startActivity(mySuperIntent);

                // remove activity from activity stack
                finish();

        }, SPLASH_TIME);
    }

    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(3000)
                .start();
    }
}
