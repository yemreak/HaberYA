package com.iuce.news.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iuce.news.R;
import com.iuce.news.api.NewsAPI;
import com.iuce.news.api.NewsAPIOptions;
import com.iuce.news.db.entity.News;

import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    public static final String SEARCH_QUERY = "query";
    private static String query;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.search_recycler_view);

        query = getIntent().getStringExtra(SEARCH_QUERY);
        getRequestedNews();
    }

    private void getRequestedNews() {
        NewsAPIOptions options = NewsAPIOptions.Builder().setQuery(query).build();
        if (isConnected()) {
            NewsAPI.requestTopHeadlines(this, this::initRecyclerView, options);
        }
    }

    private void initRecyclerView(List<News> newsList) {
        SearchAdapter searchAdapter = new SearchAdapter(this, newsList);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
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
