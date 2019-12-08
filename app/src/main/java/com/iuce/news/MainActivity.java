package com.iuce.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView(){
        NewsAPI.requestNewsDatas(this, (newsDataList -> {
            RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
            NewsAdapter newsAdapter = new NewsAdapter(this, newsDataList);
            recyclerView.setAdapter(newsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }));
    }
}
