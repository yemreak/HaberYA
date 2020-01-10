package com.iuce.news.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;
import com.iuce.news.viewmodel.NewsViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
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
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.news_recycler_view);
        drawerLayout = findViewById(R.id.main_activity_drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
                    default:
                        return true;
                }
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // lifecycle-extensions:$arch_lifecycle:2.2.0-beta01 versiyonuna uygun :'(
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getAllNewsWithState().observe(this, this::initRecyclerView);

        swipeRefreshLayout = findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getNewNews();
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
            NewsAPI.requestNewsData(this, (this::saveToDB));
        }
        newsViewModel.getAllNewsWithState().observe(this, this::fillView);
    }

    private void saveToDB(List<News> newsList) {
        newsViewModel.insertNews(newsList.toArray(new News[0]));
    }

    private void fillView(List<NewsWithState> newsWithStateList) {
        NewsAdapter newsAdapter = new NewsAdapter(this, newsWithStateList);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setRefreshing(false);
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    /*
     * Details:
     * https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-2-user-experience/lesson-4-user-interaction/4-3-c-menus-and-pickers/4-3-c-menus-and-pickers.html
     */
    //  getMenuInflater().inflate(R.menu.main_layout_menu, menu);
    //return true;
    //}

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

    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = Objects.requireNonNull(networkInfo).isConnected();

        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = Objects.requireNonNull(networkInfo).isConnected();

        return isWifiConn || isMobileConn;
    }


}
