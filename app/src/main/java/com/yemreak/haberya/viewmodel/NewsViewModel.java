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
import java.util.Objects;

public class NewsViewModel extends AndroidViewModel {

    public static final String TAG = "NewsViewModel";
    public static final int LIMIT_NEWS = 400;
    public static final int COUNT_DEL = 200;

    private NewsRepository repository;
    private LiveData<List<NewsWithState>> allNewsWithState;

    public NewsViewModel(Application application) {
        super(application);
        repository = NewsRepository.getInstance(application);

        allNewsWithState = repository.getAllNewsWithState();
    }

    public void insertNews(News... news) {
        clearDB();
        repository.insertNews(news);
    }

    public void clearDB() {
        try {
            int size = Objects.requireNonNull(allNewsWithState.getValue()).size();
            Log.d(TAG, "clearDB: News count: " + size);

            if (size >= LIMIT_NEWS) {
                Log.d(TAG, "clearDB: News deletion has been started.");
                repository.deleteRow(COUNT_DEL);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

    public void deleteNewsByIDList(Long... idList) {
        repository.deleteNewsByIDList(idList);
    }

}
