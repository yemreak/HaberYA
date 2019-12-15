package com.iuce.news;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
        initRecyclerView();
        //nvm= new ViewModelProvider(this).get(NewsViewModel.class);

    }

    private void initRecyclerView(){
        NewsAPI.requestNewsDatas(this, (newsDataList -> {
            RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
            NewsAdapter newsAdapter = new NewsAdapter(this, newsDataList);
            recyclerView.setAdapter(newsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            saveToDB(newsDataList);


        }));
    }

    private void saveToDB( ArrayList<News> newsData){
        // lifecycle-extensions:$arch_lifecycle:2.2.0-beta01 versiyonuna uygun :'(
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.insert(newsData.toArray(new News[newsData.size()]));

    }
}
