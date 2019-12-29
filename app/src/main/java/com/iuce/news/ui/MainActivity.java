package com.iuce.news.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iuce.news.NewsAPI;
import com.iuce.news.R;
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        if (isConnected()) {
            NewsAPI.requestNewsData(this, (newsList -> {
                ArrayList<NewsWithState> newsWithStateList = appendStateToNews(newsList);
                fillView(newsWithStateList);
                saveToDB(newsWithStateList);
            }));
        } else {
            newsViewModel.getAllNewsWithState().observe(this,
                    newsWithStates -> fillView(new ArrayList<>(newsWithStates)));
        }
    }

    private ArrayList<NewsWithState> appendStateToNews(ArrayList<News> newsList) {
        ArrayList<NewsWithState> newsWithStateList = new ArrayList<>();
        for (News aNews : newsList) {
            newsWithStateList.add(
                    new NewsWithState(
                            aNews,
                            Collections.singletonList(new State(
                                    aNews.getId(),
                                    State.NAME_FEED
                            ))
                    )
            );
        }
        return newsWithStateList;
    }

    private void fillView(ArrayList<NewsWithState> newsWithStateList) {
        RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
        com.iuce.news.ui.NewsAdapter newsAdapter = new NewsAdapter(this, newsWithStateList);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveToDB(ArrayList<NewsWithState> newsWithStateList) {
        newsViewModel.deleteOnlyFeed();
        newsViewModel.insertNewsWithState(newsWithStateList.toArray(new NewsWithState[0]));
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
