package com.yemreak.haberya.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.yemreak.haberya.R;
import com.yemreak.haberya.api.AdBlocker;
import com.yemreak.haberya.api.NewsAPI;
import com.yemreak.haberya.api.newsapi.Options;
import com.yemreak.haberya.api.newsapi.SOptions;
import com.yemreak.haberya.api.newsapi.THOptions;
import com.yemreak.haberya.db.entity.News;
import com.yemreak.haberya.db.entity.State;
import com.yemreak.haberya.db.pojo.NewsWithState;
import com.yemreak.haberya.ui.adapters.NewsAdapter;
import com.yemreak.haberya.viewmodel.NewsViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

	public static final String TAG = "MainActivity";

	public static Options.Category currentCategory = null;
	public static Options.Country currentCountry = Options.Country.TR;

	public static int savedPosition = -1;
	public static int top = -1;

	private NewsViewModel newsViewModel;
	private SwipeRefreshLayout swipeRefreshLayout;
	private LinearLayoutManager linearLayoutManager;
	private RecyclerView recyclerView;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initNavigationDrawer();

		linearLayoutManager = new LinearLayoutManager(this);
		recyclerView = findViewById(R.id.news_recycler_view);

		// lifecycle-extensions:$arch_lifecycle:2.2.0-beta01 versiyonuna uygun :'(
		newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
		newsViewModel.getAllNewsWithState().observe(this, this::initRecyclerView);

		swipeRefreshLayout = findViewById(R.id.refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this::getNewNews);

		swipeRefreshLayout.setOnRefreshListener(() -> {
			if (currentCountry == THOptions.Country.TR && currentCategory == null) {
				getNewNews();
			} else if (currentCategory != null) {
				getCategorizedNews(currentCategory);
			} else {
				getCountryNews(currentCountry);
			}
		});

		AdBlocker.init(this);

		// TODO: 2/5/2020 Asmaa Mirkhan ~ Varsayılan ayarları tanımlaama
		THOptions.Builder.setDefaultCountry(Options.Country.TR);
		SOptions.Builder.setDefaultCountry(Options.Country.TR);
	}

	@Override
	protected void onPause() {
		super.onPause();
		currentCountry = THOptions.Country.TR;
		saveRecyclerViewPosition();
	}

	@Override
	protected void onResume() {
		super.onResume();
		restoreRecyclerViewPosition();
	}

	private void saveRecyclerViewPosition() {
		savedPosition = linearLayoutManager.findFirstVisibleItemPosition();
		View view = recyclerView.getChildAt(0);
		// TODO: getLeft() çalışma mantığı gözden geçirilecek
		top = (view == null) ? 0 : (view.getTop() - view.getLeft() - recyclerView.getPaddingTop());
	}

	private void restoreRecyclerViewPosition() {
		if (savedPosition != -1) {
			linearLayoutManager.scrollToPositionWithOffset(savedPosition, top);
		}
	}

	private void initRecyclerView(List<NewsWithState> newsWithStateList) {
		if (newsWithStateList.size() == 0) {
			getNewNews();
		} else {
			fillView(newsWithStateList);
		}
	}

	private void getNewNews() {
		currentCountry = THOptions.Country.TR;
		if (isConnected()) {
			NewsAPI.requestTopHeadlines(
					this,
					this::saveToDB,
					THOptions.Builder().setCountry(currentCountry).build()
			);
		}
		newsViewModel.getAllNewsWithState().observe(this, this::fillView);
	}

	private void saveToDB(List<News> newsList) {
		newsViewModel.insertNews(newsList.toArray(new News[0]));
	}

	private void fillView(List<NewsWithState> newsWithStateList) {
		saveRecyclerViewPosition();
		NewsAdapter newsAdapter = new NewsAdapter(this, newsWithStateList);
		recyclerView.setAdapter(newsAdapter);
		recyclerView.setLayoutManager(linearLayoutManager);
		swipeRefreshLayout.setRefreshing(false);
		restoreRecyclerViewPosition();
	}

	private void initNavigationDrawer() {
		drawerLayout = findViewById(R.id.main_activity_drawer_layout);
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
		drawerLayout.addDrawerListener(actionBarDrawerToggle);
		actionBarDrawerToggle.syncState();

		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		NavigationView navigationView = findViewById(R.id.navigation_view);

		navigationView.setNavigationItemSelectedListener((MenuItem menuItem) -> {
					int id = menuItem.getItemId();
					switch (id) {
						case R.id.get_all_reacted_but:
							switchActivity(null);
							return true;
						case R.id.get_liked_but:
							switchActivity(State.Type.LIKED);
							return true;
						case R.id.get_saved_but:
							switchActivity(State.Type.LATER);
							return true;
						case R.id.get_middle_east_item:
							// TODO: birden fazla ülke seçimi imkanı sunulabilir (?)
							// Birleşik arap emirlikeri
							getCountryNews(THOptions.Country.AE);
							return true;
						case R.id.get_usa_item:
							// Amerika
							getCountryNews(THOptions.Country.US);
							return true;
						case R.id.get_europe_item:
							// İngiltere
							getCountryNews(THOptions.Country.GB);
							return true;
						case R.id.get_australia_item:
							// Avustralya
							getCountryNews(THOptions.Country.AU);
							return true;
						case R.id.get_asia_item:
							// Malezya
							getCountryNews(THOptions.Country.MY);
							return true;
						case R.id.get_all_news_item:
							drawerLayout.closeDrawers();
							getNewNews();
							return true;
						case R.id.get_science_item:
							getCategorizedNews(THOptions.Category.SCIENCE);
							return true;
						case R.id.get_technology_item:
							getCategorizedNews(THOptions.Category.TECHNOLOGY);
							return true;
						case R.id.get_health_item:
							getCategorizedNews(THOptions.Category.HEALTH);
							return true;
						case R.id.get_business_item:
							getCategorizedNews(THOptions.Category.BUSINESS);
							return true;
						case R.id.get_entertainment_item:
							getCategorizedNews(THOptions.Category.ENTERTAINMENT);
							return true;
						case R.id.get_sport_item:
							getCategorizedNews(THOptions.Category.SPORTS);
							return true;
						default:
							return true;
					}
				}
		);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (actionBarDrawerToggle.onOptionsItemSelected(item))
			return true;
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.search_bar_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.search_button);
		SearchView searchView = null;

		if (searchItem != null) {
			searchView = (SearchView) searchItem.getActionView();
		}

		if (searchView != null) {

			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
					intent.putExtra(SearchActivity.SEARCH_QUERY, query);
					MainActivity.this.startActivity(intent);
					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					return false;
				}
			});

		}
		return super.onCreateOptionsMenu(menu);

	}

	public void switchActivity(State.Type type) {
		Intent intent = new Intent(this, ReactedActivity.class);
		intent.putExtra(ReactedActivity.NAME_STATE_TYPE, type);
		this.startActivity(intent);
	}

	private void getCategorizedNews(THOptions.Category category) {
		THOptions options = THOptions.Builder().setCategory(category).build();
		drawerLayout.closeDrawers();
		if (isConnected()) {
			NewsAPI.requestTopHeadlines(this, this::saveToDB, options);
		}
		currentCountry = THOptions.Country.TR;
		currentCategory = options.getCategory();
		newsViewModel.getNewsByCategory(options.getCategory()).observe(this, this::fillView);
	}

	private void getCountryNews(THOptions.Country country) {
		THOptions options = THOptions.Builder().setCountry(country).build();
		drawerLayout.closeDrawers();
		if (isConnected()) {
			NewsAPI.requestTopHeadlines(this, this::saveToDB, options);
		}
		currentCategory = null;
		currentCountry = options.getCountry();
		newsViewModel.getNewsByCountry(options.getCountry()).observe(this, this::fillView);
	}

	private boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = Objects.requireNonNull(networkInfo).isConnected();

		networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = Objects.requireNonNull(networkInfo).isConnected();

		return isWifiConn || isMobileConn;
	}

	public static class NewsActivity extends AppCompatActivity {

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
			Intent chooser = Intent.createChooser(shareIntent, "title");

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
}
