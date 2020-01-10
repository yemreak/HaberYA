package com.iuce.news.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.iuce.news.R;
import com.iuce.news.api.AdBlocker;

import java.util.HashMap;
import java.util.Map;

public class OriginalNews extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_news);
        // getSupportActionBar().hide();

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        if (url != null) {
            WebView webView = findViewById(R.id.original_web_view);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                private final Map<String, Boolean> loadedUrls = new HashMap<>();

                @SuppressWarnings("ConstantConditions")
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                    boolean ad;
                    if (!loadedUrls.containsKey(url)) {
                        ad = AdBlocker.isAd(url);
                        loadedUrls.put(url, ad);
                    } else {
                        ad = loadedUrls.get(url);
                    }
                    return ad ? AdBlocker.createEmptyResource() :
                            super.shouldInterceptRequest(view, url);
                }
            });

            // HTTP protokolleri WebView'da düzgün çalışmıyor ve güvenli değil
            url = url.replace("http://", "https://");
            webView.loadUrl(url);
        }
    }
}
