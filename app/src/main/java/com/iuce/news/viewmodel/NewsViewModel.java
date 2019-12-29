package com.iuce.news.viewmodel;

import android.app.Application;

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

    public void insertNewsWithState(NewsWithState... newsWithStates) {
        ArrayList<News> newsList = new ArrayList<>();
        ArrayList<State> stateList = new ArrayList<>();

        for (NewsWithState newsWithState : newsWithStates) {
            newsList.add(newsWithState.getNews());
            stateList.addAll(newsWithState.getStates());
        }

        repository.insertNews(newsList.toArray(new News[0]));
        repository.insertState(stateList.toArray(new State[0]));
    }

    public void deleteOnlyFeed() {
        repository.deleteOnlyFeed();
    }
}
