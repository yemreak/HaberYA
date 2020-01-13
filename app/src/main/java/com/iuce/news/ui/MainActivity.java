package com.iuce.news.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.iuce.news.R;
import com.iuce.news.api.AdBlocker;
import com.iuce.news.api.NewsAPI;
import com.iuce.news.api.NewsAPIOptions;
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;
import com.iuce.news.viewmodel.NewsViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static NewsAPIOptions.Category currentCategory = NewsAPIOptions.Category.ANY;
    public static NewsAPIOptions.Country currentCountry = NewsAPIOptions.Country.TR;

    public static int savedPosition = -1;
    public static int top = -1;
    private NewsViewModel newsViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

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
            if (currentCountry == NewsAPIOptions.Country.TR && currentCategory == NewsAPIOptions.Category.ANY) {
                getNewNews();
            } else if (currentCategory != NewsAPIOptions.Category.ANY) {
                getNewNews(NewsAPIOptions.Builder().setCategory(currentCategory).build());
            } else {
                getNewNews(NewsAPIOptions.Builder().setCountry(currentCountry).build());
            }
        });

        AdBlocker.init(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savedPosition = linearLayoutManager.findFirstVisibleItemPosition();
        View view = recyclerView.getChildAt(0);
        top = (view == null) ? 0 : (view.getTop() - recyclerView.getPaddingTop());
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if (isConnected()) {
            NewsAPI.requestTopHeadlines(this, this::saveToDB, null);
        }
        newsViewModel.getAllNewsWithState().observe(this, this::fillView);
    }

    private void getNewNews(NewsAPIOptions options) {
        if (isConnected()) {
            NewsAPI.requestTopHeadlines(this, this::saveToDB, options);
        }
        if (options.getCategory() != null) {
            currentCountry = NewsAPIOptions.Country.TR;
            currentCategory = NewsAPIOptions.Category.valueOf(options.getCategory().toUpperCase());
            newsViewModel.getNewsByCategory(NewsAPIOptions.Category.valueOf(options.getCategory().toUpperCase())).observe(this, this::fillView);
        }
        if (options.getCountry() != null && NewsAPIOptions.Country.valueOf(options.getCountry().toUpperCase()) != NewsAPIOptions.Country.TR) {
            currentCategory = NewsAPIOptions.Category.ANY;
            currentCountry = NewsAPIOptions.Country.valueOf(options.getCountry().toUpperCase());
            newsViewModel.getNewsByCountry(NewsAPIOptions.Country.valueOf(options.getCountry().toUpperCase())).observe(this, this::fillView);
        }

    }

    private void saveToDB(List<News> newsList) {
        newsViewModel.insertNews(newsList.toArray(new News[0]));
    }

    private void fillView(List<NewsWithState> newsWithStateList) {
        /*for (NewsWithState nn : newsWithStateList) {
            Log.e("ESMA", nn.getNews().getCategory() + "  " + nn.getNews().getCountry());
        }*/
        NewsAdapter newsAdapter = new NewsAdapter(this, newsWithStateList);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initNavigationDrawer() {
        drawerLayout = findViewById(R.id.main_activity_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_view);

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
                            getCountryNews(NewsAPIOptions.Country.AE);
                            return true;
                        case R.id.get_all_news_item:
                            drawerLayout.closeDrawers();
                            getNewNews();
                        case R.id.get_science_item:
                            getCategorizedNews(NewsAPIOptions.Category.SCIENCE);
                            return true;
                        case R.id.get_technology_item:
                            getCategorizedNews(NewsAPIOptions.Category.TECHNOLOGY);
                            return true;
                        case R.id.get_health_item:
                            getCategorizedNews(NewsAPIOptions.Category.HEALTH);
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

    public void switchActivity(State.Type type) {
        Intent intent = new Intent(this, ReactedActivity.class);
        intent.putExtra(ReactedActivity.NAME_STATE_TYPE, type);
        this.startActivity(intent);
    }

    private void getCategorizedNews(NewsAPIOptions.Category category) {
        NewsAPIOptions options = NewsAPIOptions.Builder().setCategory(category).build();
        drawerLayout.closeDrawers();
        getNewNews(options);
    }

    private void getCountryNews(NewsAPIOptions.Country country) {
        NewsAPIOptions options = NewsAPIOptions.Builder().setCountry(country).build();
        drawerLayout.closeDrawers();
        getNewNews(options);
    }

    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = Objects.requireNonNull(networkInfo).isConnected();

        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = Objects.requireNonNull(networkInfo).isConnected();

        return isWifiConn || isMobileConn;
    }


}
