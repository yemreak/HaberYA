package com.iuce.news.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.iuce.news.R;
import com.iuce.news.api.NewsAPI;
import com.iuce.news.db.entity.News;
import com.iuce.news.viewmodel.NewsViewModel;

import java.util.List;
import java.util.Objects;


/*
 * Example: https://github.com/SabithPkcMnr/SplashScreen
 */
public class SplashScreenActivity extends AppCompatActivity {

    ProgressBar splashProgress;
    private NewsViewModel newsViewModel;

    final int SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_spalsh_screen);
        splashProgress = findViewById(R.id.splashProgress);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        new FetchAsyncTask().execute();

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);

            // remove activity from activity stack
            finish();

        }, SPLASH_TIME);
    }

    private class FetchAsyncTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void... val) {
            if (isConnected()) {
                NewsAPI.requestNewsData(getApplicationContext(), (this::saveToDB));
                publishProgress(0);
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            playProgress();
        }

        private void saveToDB(List<News> newsList) {
            newsViewModel.insertNews(newsList.toArray(new News[0]));
        }

        private boolean isConnected() {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            boolean isWifiConn = Objects.requireNonNull(networkInfo).isConnected();

            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            boolean isMobileConn = Objects.requireNonNull(networkInfo).isConnected();

            return isWifiConn || isMobileConn;
        }
    }
    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(SPLASH_TIME)
                .start();
    }
}
