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

    public NewsViewModel(Application application) {
        super(application);
        repository = NewsRepository.getInstance(application);
    }

    public void deleteNewsByIDList(Long... idList) {
        repository.deleteNewsByIDList(idList);
    }

    public void insertNews(News... news) {
        repository.insertNews(news);
    }

    public void insertState(State... states) {
        repository.insertState(states);
    }
}
