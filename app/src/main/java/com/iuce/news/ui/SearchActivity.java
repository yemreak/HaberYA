package com.iuce.news.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.iuce.news.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String query = getIntent().getStringExtra("QUERY");

    }
}
