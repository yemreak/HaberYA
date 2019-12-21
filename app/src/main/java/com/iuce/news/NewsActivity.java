package com.iuce.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_news_activity);

        News news = Globals.getInstance().getSelectedNews();

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

    /*
     * Details:
     * https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-2-user-experience/lesson-4-user-interaction/4-3-c-menus-and-pickers/4-3-c-menus-and-pickers.html
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public void onShareClick(MenuItem item) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, Globals.getInstance().getSelectedNews().getTitle() + "\n"
                + Globals.getInstance().getSelectedNews().getDescription() + "\n"
                + Globals.getInstance().getSelectedNews().getUrl());
        shareIntent.setType("text/plain");
        Intent chooser = Intent.createChooser(shareIntent, "title");
        // Resolve the intent before starting the activity
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}
