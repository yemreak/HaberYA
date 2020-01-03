package com.iuce.news.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iuce.news.R;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;
import com.iuce.news.viewmodel.NewsViewModel;

import java.util.List;

public class ReactedActivity extends AppCompatActivity {

    public static final String TAG = "ReactedActivity";
    public static final String NAME_STATE_TYPE = "sType";

    private NewsViewModel newsViewModel;
    private State.StateType TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reacted);

        TYPE = (State.StateType) getIntent().getSerializableExtra(NAME_STATE_TYPE);
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        initRecyclerView();
    }

    private void initRecyclerView() {
        if (TYPE != null) {
            newsViewModel.getAllNewsWithStateByState(TYPE).observe(this, this::fillView);

        } else {
            newsViewModel.getAllNewsWithStateHasStates().observe(this, this::fillView);
        }
    }

    private void fillView(List<NewsWithState> newsWithStateList) {
        RecyclerView recyclerView = findViewById(R.id.reacted_news_recycler_view);
        ReactedAdapter newsAdapter = new ReactedAdapter(this, newsWithStateList);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Details:
         * https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-2-user-experience/lesson-4-user-interaction/4-3-c-menus-and-pickers/4-3-c-menus-and-pickers.html
         */
        getMenuInflater().inflate(R.menu.main_layout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.get_all_reacted_but:
                switchContent(null);
                return true;
            case R.id.get_liked_but:
                switchContent(State.StateType.TYPE_LIKED);
                return true;
            case R.id.get_saved_but:
                switchContent(State.StateType.TYPE_LATER);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchContent(State.StateType type) {
        TYPE = type;
        initRecyclerView();
    }
}
