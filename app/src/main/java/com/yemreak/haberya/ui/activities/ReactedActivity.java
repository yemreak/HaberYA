package com.yemreak.haberya.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yemreak.haberya.R;
import com.yemreak.haberya.db.entity.State;
import com.yemreak.haberya.db.pojo.NewsWithState;
import com.yemreak.haberya.ui.adapters.ReactedAdapter;
import com.yemreak.haberya.viewmodel.NewsViewModel;

import java.util.List;

public class ReactedActivity extends AppCompatActivity {

	public static final String TAG = "ReactedActivity";
	public static final String NAME_STATE_TYPE = "sType";


	public static int savedPosition = -1;
	public static int top = -1;
	private LinearLayoutManager reactedLinearLayoutManager;
	private RecyclerView reactedRecyclerView;
	private NewsViewModel newsViewModel;
	private State.Type TYPE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reacted);

		reactedLinearLayoutManager = new LinearLayoutManager(this);
		reactedRecyclerView = findViewById(R.id.reacted_news_recycler_view);

		setBackButton();
		TYPE = (State.Type) getIntent().getSerializableExtra(NAME_STATE_TYPE);
		newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

		initRecyclerView();
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveRecyclerViewPosition();
	}

	@Override
	protected void onResume() {
		super.onResume();
		restoreRecyclerViewPosition();
	}

	private void saveRecyclerViewPosition() {
		savedPosition = reactedLinearLayoutManager.findFirstVisibleItemPosition();
		View view = reactedRecyclerView.getChildAt(0);
		top = (view == null) ? 0 : (view.getTop() - view.getLeft() - reactedRecyclerView.getPaddingTop());
	}

	private void restoreRecyclerViewPosition() {
		if (savedPosition != -1) {
			reactedLinearLayoutManager.scrollToPositionWithOffset(savedPosition, top);
		}
	}

	private void initRecyclerView() {
		if (TYPE != null) {
			newsViewModel.getAllNewsWithStateByStates(TYPE).observe(this, this::fillView);

		} else {
			newsViewModel.getAllNewsWithStateHasStates().observe(this, this::fillView);
		}
	}

	private void fillView(List<NewsWithState> newsWithStateList) {
		saveRecyclerViewPosition();
		ReactedAdapter newsAdapter = new ReactedAdapter(this, newsWithStateList);
		reactedRecyclerView.setAdapter(newsAdapter);
		reactedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		restoreRecyclerViewPosition();
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
