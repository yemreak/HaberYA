package com.iuce.news;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository repository;

    private LiveData<List<News>> allNews;

    public NewsViewModel (Application application) {
        super(application);
        repository = new NewsRepository(application);
        allNews = repository.getNewsList();
    }

    LiveData<List<News>> getAllNews() { return allNews; }

    public void insert(News... news) { repository.insert(news); }
}
