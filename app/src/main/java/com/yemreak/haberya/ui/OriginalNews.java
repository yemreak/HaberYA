package com.yemreak.haberya.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.yemreak.haberya.R;
import com.yemreak.haberya.api.AdBlocker;

import java.util.HashMap;
import java.util.Map;

public class OriginalNews extends AppCompatActivity {

    public static final String NEWS_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_news);
        // getSupportActionBar().hide();

        setBackButton();

        Intent intent = getIntent();
        String url = intent.getStringExtra(NEWS_URL);
        if (url != null) {
            WebView webView = findViewById(R.id.original_web_view);
            ProgressBar webProgressBar = findViewById(R.id.web_progressbar);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            // https://www.hidroh.com/2016/05/19/hacking-up-ad-blocker-android/
            webView.setWebViewClient(new WebViewClient() {
                private final Map<String, Boolean> loadedUrls = new HashMap<>();

                // Details: https://stackoverflow.com/a/21458311
                // More detailed: https://developer.android.com/reference/android/webkit/WebViewClient#public-methods_1
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    webProgressBar.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    webProgressBar.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }

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

    private void setBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
