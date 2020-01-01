package com.iuce.news.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import com.iuce.news.db.entity.State;
import com.iuce.news.viewmodel.NewsViewModel;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {
    private NewsViewModel newsViewModel;

    TextView source;
    TextView date;
    TextView title;
    TextView description;
    TextView content;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_news_activity);

        initViews();
        fillView();

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.insertStates(
                new State(
                        Globals.getInstance().getSelectedNewsWithState().getNews().getId(),
                        State.TYPE_READ
                )
        );
    }

    public void initViews() {
        source = findViewById(R.id.news_source);
        date = findViewById(R.id.news_date);
        title = findViewById(R.id.news_title);
        description = findViewById(R.id.news_description);
        content = findViewById(R.id.news_content);
        image = findViewById(R.id.news_image);
    }

    public void fillView() {
        News news = Globals.getInstance().getSelectedNewsWithState().getNews();
        
        source.setText(news.getSource());
        date.setText(news.getPublishedAt());
        description.setText(news.getDescription());
        content.setText(news.getContent());
        title.setText(news.getTitle());
        

        // Details: https://stackoverflow.com/a/40440694
        Picasso.get()
                .load(Uri.parse(news.getUrlToImage()))
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
        
        News news = Globals.getInstance().getSelectedNewsWithState().getNews();
        shareIntent.putExtra(Intent.EXTRA_TEXT, news.getTitle() + "\n"
                + news.getDescription() + "\n"
                + news.getUrl());
        shareIntent.setType("text/plain");
        Intent chooser = Intent.createChooser(shareIntent, "title");

        // Resolve the intent before starting the activity
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    public void onReactClick(MenuItem item) {
        // TODO: Reaction fonksiyonelliği buraya eklenecek
    }
}
