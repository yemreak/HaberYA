package com.iuce.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NewsViewModel newsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // lifecycle-extensions:$arch_lifecycle:2.2.0-beta01 versiyonuna uygun :'(
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        initRecyclerView();
    }

    private void initRecyclerView(){
        if(is_connected()) {
            NewsAPI.requestNewsDatas(this, (newsDataList -> {
                fillView(newsDataList);
                saveToDB(newsDataList);
            }));
        }else{
            newsViewModel.getAllNews().observe(this, new Observer<List<News>>() {
                @Override
                public void onChanged(List<News> news) {
                    fillView(new ArrayList<News>(news));
                }
            });
        }
    }

    private void fillView(ArrayList<News> news){
        RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
        NewsAdapter newsAdapter = new NewsAdapter(this, news);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveToDB( ArrayList<News> newsData){
        newsViewModel.insert(newsData.toArray(new News[newsData.size()]));
    }

    private boolean is_connected(){
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        if(isWifiConn || isMobileConn)
            return true;
        else
            return false;
    }
}
