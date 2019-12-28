package com.iuce.news.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iuce.news.NewsAPI;
import com.iuce.news.R;
import com.iuce.news.db.entity.News;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private com.iuce.news.viewmodel.NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // lifecycle-extensions:$arch_lifecycle:2.2.0-beta01 versiyonuna uygun :'(
        newsViewModel = new ViewModelProvider(this).get(com.iuce.news.viewmodel.NewsViewModel.class);
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (is_connected()) {
            NewsAPI.requestNewsData(this, (newsDataList -> {
                fillView(newsDataList);
                saveToDB(newsDataList);
            }));
        } else {
            newsViewModel.getAllNews().observe(this, news -> fillView(new ArrayList<>(news)));
        }
    }

    private void fillView(ArrayList<News> news) {
        RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
        com.iuce.news.ui.NewsAdapter newsAdapter = new com.iuce.news.ui.NewsAdapter(this, news);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveToDB(ArrayList<News> newsData) {
        newsViewModel.delete();
        newsViewModel.insert(newsData.toArray(new News[0]));
    }

    private boolean is_connected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = Objects.requireNonNull(networkInfo).isConnected();

        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = Objects.requireNonNull(networkInfo).isConnected();

        return isWifiConn || isMobileConn;
    }
}
