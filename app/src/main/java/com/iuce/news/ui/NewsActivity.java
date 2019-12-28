package com.iuce.news.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuce.news.Globals;
import com.iuce.news.R;
import com.iuce.news.db.entity.News;
import com.iuce.news.viewmodel.NewsViewModel;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {
    private NewsViewModel newsViewModel;
    private News NEWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_news_activity);
        NEWS = Globals.getInstance().getSelectedNews();
        fillView();
    }

    public void fillView(){
        TextView source = findViewById(R.id.news_source);
        TextView date = findViewById(R.id.news_date);
        TextView title = findViewById(R.id.news_title);
        TextView description = findViewById(R.id.news_description);
        TextView content = findViewById(R.id.news_content);
        ImageView image = findViewById(R.id.news_image);
        source.setText(NEWS.getSource());
        date.setText(NEWS.getPublishedAt());
        description.setText(NEWS.getDescription());
        content.setText(NEWS.getContent());
        title.setText(NEWS.getTitle());
        /*
         * Details: https://stackoverflow.com/a/40440694
         */
        Picasso.get()
                .load(Uri.parse(NEWS.getUrlToImage()))
                .into(image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Details:
         * https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-2-user-experience/lesson-4-user-interaction/4-3-c-menus-and-pickers/4-3-c-menus-and-pickers.html
         */
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public void onShareClick(MenuItem item) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, NEWS.getTitle() + "\n"
                + NEWS.getDescription() + "\n"
                + NEWS.getUrl());
        shareIntent.setType("text/plain");
        Intent chooser = Intent.createChooser(shareIntent, "title");
        // Resolve the intent before starting the activity
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    public void onReactClick(MenuItem item){
        // Reaction fonksiyonelliği buraya eklenecek
    }
}
