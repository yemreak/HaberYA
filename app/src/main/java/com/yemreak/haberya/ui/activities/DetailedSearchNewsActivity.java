package com.yemreak.haberya.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.yemreak.haberya.Globals;
import com.yemreak.haberya.R;
import com.yemreak.haberya.db.entity.News;

public class DetailedSearchNewsActivity extends AppCompatActivity {

	public static final String TAG = "NewsActivity";
	public static final String NAME_NEWS_ID = "newsID";

	TextView source;
	TextView date;
	TextView title;
	TextView description;
	TextView content;
	ImageView image;
	ImageButton btn_web;
	private News selectedNews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_news_activity);
		setBackButton();
		initViews();
		selectedNews = Globals.getInstance().getSelectedNews();
		fillViews();

	}

	public void initViews() {
		source = findViewById(R.id.news_source);
		date = findViewById(R.id.news_date);
		title = findViewById(R.id.news_title);
		description = findViewById(R.id.news_description);
		content = findViewById(R.id.news_content);
		image = findViewById(R.id.news_image);
		btn_web = findViewById(R.id.btn_show_in_web);
	}

	public void fillViews() {
		source.setText(selectedNews.getSource());
		date.setText(selectedNews.getPublishedAt());
		description.setText(selectedNews.getDescription());
		content.setText(selectedNews.getContent());
		title.setText(selectedNews.getTitle());

		Picasso.get()
				.load(Uri.parse(selectedNews.getUrlToImage()))
				.into(image);
	}

	public void openInWeb(View v) {
		Intent webIntent = new Intent(this, OriginalNewsActivity.class);
		webIntent.putExtra("URL", selectedNews.getUrl());
		this.startActivity(webIntent);
	}

	private void setBackButton() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}
}
