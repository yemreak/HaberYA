package com.iuce.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<NewsData> newsData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNews();
    }

    private void initNews(){
        // Burası test amaçlı bir kısımdır
        // Yunus'un kodu buradan çağrılacak
        ////////////////
        NewsData sample = new NewsData();
        sample.title = "First Title";
        sample.description = "First Description";
        sample.urlToImage = "https://i.pstimaj.com/img/75/0x0/5decabd9ae298b59d09385a1";
        newsData.add(sample);
        newsData.add(sample);
        newsData.add(sample);
        newsData.add(sample);
        newsData.add(sample);
        ////////////////
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
        NewsAdapter newsAdapter = new NewsAdapter(this, newsData);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
