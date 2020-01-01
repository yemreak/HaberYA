package com.iuce.news.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iuce.news.NewsRepository;
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository repository;

    private LiveData<List<NewsWithState>> allNewsWithState;

    public NewsViewModel(Application application) {
        super(application);
        repository = NewsRepository.getInstance(application);
        allNewsWithState = repository.getAllNewsWithState();
    }

    public void deleteNewsByIDList(Long... idList) {
        repository.deleteNewsByIDList(idList);
    }

    public void insertNews(News... news) {
        repository.insertNews(news);
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
}
