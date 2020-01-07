package com.iuce.news.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.iuce.news.R;

public class OriginalNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_news);
        // getSupportActionBar().hide();

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");

        WebView webView = findViewById(R.id.original_web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }
}
