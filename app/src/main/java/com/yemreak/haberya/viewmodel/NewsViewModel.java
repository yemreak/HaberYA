package com.yemreak.haberya.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yemreak.haberya.NewsRepository;
import com.yemreak.haberya.api.newsapi.Options;
import com.yemreak.haberya.api.newsapi.THOptions;
import com.yemreak.haberya.db.entity.News;
import com.yemreak.haberya.db.entity.State;
import com.yemreak.haberya.db.pojo.NewsWithState;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

	// TODO: 2/5/2020 Yunus Emre AK - ViewModel üzerinde Async işlemler yapılmalı, repo içerisinde core metotlar olmalı

	public static final String TAG = "NewsViewModel";
	public static final int COUNT_DEL = 200;

	private static int STATELESS_NEWS_LIMIT = 400;

	private NewsRepository repository;
	private LiveData<List<NewsWithState>> allNewsWithState;

	public NewsViewModel(Application application) {
		super(application);
		repository = NewsRepository.getInstance(application);

		allNewsWithState = repository.getAllNewsWithState();
	}

	/**
	 * DB'de saklanacak toplam haber limiti
	 *
	 * @param statelessNewsLimit
	 */
	public static void setStatelessNewsLimit(int statelessNewsLimit) {
		STATELESS_NEWS_LIMIT = statelessNewsLimit;
	}

	public void insertNews(News... news) {
		countAllStatelessNews(size -> {
			Log.d(TAG, "insertNews: Kayıtlı haber sayısı: " + size);
			if (size > STATELESS_NEWS_LIMIT) clearDB();
			repository.insertNews(news);
		});
	}

	public void clearDB() {
		Log.d(TAG, "clearDB: Eski haberler temizlendi");
		repository.deleteRow(COUNT_DEL);
	}

	public void insertStates(State... states) {
		repository.insertStates(states);
	}

	public void deleteStates(State... states) {
		repository.deleteStates(states);
	}

	public LiveData<List<NewsWithState>> getAllNewsWithState() {
		return allNewsWithState;
	}

	public LiveData<List<NewsWithState>> getAllNewsWithStateHasStates() {
		return repository.getAllNewsWithStateHasStates();
	}

	public LiveData<List<NewsWithState>> getAllNewsWithStateByStates(State.Type... types) {
		return repository.getNewsWithStateByStates(types);
	}

	public LiveData<List<NewsWithState>> getNewsWithStateByIDs(Integer... stateIds) {
		return repository.getNewsWithStateByIDs(stateIds);
	}

	public LiveData<List<NewsWithState>> getNewsByCategory(Options.Category... categories) {
		return repository.getNewsWithStateByCategories(categories);
	}

	public LiveData<List<NewsWithState>> getNewsByCountry(THOptions.Country... countries) {
		return repository.getNewsWithStateByCountries(countries);
	}

	public LiveData<List<NewsWithState>> getAllNewsWithStateByTitle(String title) {
		return repository.getAllNewsWithStateByTitle(title);
	}

	public LiveData<List<NewsWithState>> getAllNewsWithStateByTitleAndTypes(String title, State.Type... types) {
		return repository.getAllNewsWithStateByTitleAndTypes(title, types);
	}

	public void deleteNewsByIDList(Long... idList) {
		repository.deleteNewsByIDList(idList);
	}

	public void countAllNews(NewsRepository.ResultListener resultListener) {
		repository.countAllNews(resultListener);
	}

	public void countAllNewsWithState(NewsRepository.ResultListener resultListener) {
		repository.countAllNewsWithState(resultListener);
	}

	public void countAllStatelessNews(NewsRepository.ResultListener resultListener) {
		repository.countAllStatelessNews(resultListener);
	}

}
