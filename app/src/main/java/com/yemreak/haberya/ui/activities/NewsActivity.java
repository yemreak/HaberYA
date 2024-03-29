package com.yemreak.haberya.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;
import com.yemreak.haberya.R;
import com.yemreak.haberya.db.entity.News;
import com.yemreak.haberya.db.entity.State;
import com.yemreak.haberya.db.pojo.NewsWithState;
import com.yemreak.haberya.viewmodel.NewsViewModel;

public class NewsActivity extends AppCompatActivity {

	public static final String TAG = "NewsActivity";
	public static final String NAME_NEWS_ID = "newsID";
	TextView source;
	TextView date;
	TextView title;
	TextView description;
	TextView content;
	ImageView image;
	ImageButton btn_web;
	private NewsViewModel newsViewModel;
	private NewsWithState selectedNewsWithState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_news_activity);

		setBackButton();
		initViews();

		newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
		newsViewModel.getNewsWithStateByIDs(getIntent().getIntExtra(NAME_NEWS_ID, 0)).observe(this,
				newsWithStates -> {
					selectedNewsWithState = newsWithStates.get(0);
					fillView(selectedNewsWithState.getNews());
					newsViewModel.insertStates(State.Builder(selectedNewsWithState, State.Type.READ));
				}
		);
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

	public void fillView(News news) {
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

	public void openInWeb(View v) {
		Intent webIntent = new Intent(this, OriginalNewsActivity.class);
		webIntent.putExtra(OriginalNewsActivity.NEWS_URL, selectedNewsWithState.getNews().getUrl());
		this.startActivity(webIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * Details:
		 * https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-2-user-experience/lesson-4-user-interaction/4-3-c-menus-and-pickers/4-3-c-menus-and-pickers.html
		 */
		getMenuInflater().inflate(R.menu.action_bar_menu, menu);

		if (State.Type.LIKED.isExist(selectedNewsWithState.getStates())) {
			menu.findItem(R.id.fav_button).setIcon(R.drawable.ic_favorite_white_24dp);
		} else {
			menu.findItem(R.id.fav_button).setIcon(R.drawable.ic_favorite_border_white_24dp);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.fav_button:
				toggleLikeIcon(item);
				return true;
			case R.id.share_button:
				onShareClick(item);
				return true;
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onShareClick(MenuItem item) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);

		shareIntent.putExtra(Intent.EXTRA_TEXT, selectedNewsWithState.getNews().getTitle() + "\n"
				+ selectedNewsWithState.getNews().getDescription() + "\n"
				+ selectedNewsWithState.getNews().getUrl());
		shareIntent.setType("text/plain");
		Intent chooser = Intent.createChooser(shareIntent, getResources().getString(R.string.share_news));

		// Resolve the intent before starting the activity
		if (shareIntent.resolveActivity(getPackageManager()) != null) {
			startActivity(chooser);
		}
	}


	public void toggleLikeIcon(MenuItem item) {
		State state = State.Type.LIKED.findState(selectedNewsWithState.getStates());
		if (state != null) {
			newsViewModel.deleteStates(state);
			item.setIcon(R.drawable.ic_favorite_border_white_24dp);
		} else {
			newsViewModel.insertStates(State.Builder(selectedNewsWithState, State.Type.LIKED));
			item.setIcon(R.drawable.ic_favorite_white_24dp);
		}

		Log.d(TAG, "toggleLikeIcon: " + selectedNewsWithState.getStates());
	}

	private void setBackButton() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
	}

}
