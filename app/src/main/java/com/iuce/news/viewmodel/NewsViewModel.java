package com.iuce.news.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iuce.news.NewsRepository;
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository repository;

    private LiveData<List<NewsWithState>> allNewWithState;

    public NewsViewModel(Application application) {
        super(application);
        repository = new NewsRepository(application);
        allNewWithState = repository.getAllNewsWithState();
    }

    public LiveData<List<NewsWithState>> getAllNewsWithState() {
        return allNewWithState;
    }

    public void insertFeedNews(News... news) {
        repository.insertFeedNews(news);
    }

    public void insertState(State... states) {
        repository.insertState(states);
    }

    public void deleteOnlyFeed() {
        repository.deleteOnlyFeed();
    }
}
