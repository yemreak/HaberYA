package com.iuce.news;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_news_activity);
        News news = News.getInstance();
        TextView source = findViewById(R.id.news_source);
        TextView date = findViewById(R.id.news_date);
        TextView title = findViewById(R.id.news_title);
        TextView description = findViewById(R.id.news_description);
        TextView content = findViewById(R.id.news_content);
        ImageView image = findViewById(R.id.news_image);
        Picasso.get()
                .load(Uri.parse(news.getUrlToImage()))
                .resize(6000, 2000)
                .onlyScaleDown()
                .centerInside()
                .into(image);
        source.setText(news.getSource());
        date.setText(news.getPublishedAt());
        description.setText(news.getDescription());
        content.setText(news.getContent());
        title.setText(news.getTitle());
    }
}
