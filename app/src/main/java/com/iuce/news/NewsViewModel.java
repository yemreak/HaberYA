package com.iuce.news;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository repository;

    private LiveData<List<News>> allNews;
    private LiveData<List<Reaction>> allReaction;
    private LiveData<List<ReactionToNews>> allReactionToNews;

    public NewsViewModel(Application application) {
        super(application);
        repository = new NewsRepository(application);
        allNews = repository.getAllNews();
        allReaction = repository.getAllReaction();
        allReactionToNews = repository.getAllReactionToNews();
    }

    LiveData<List<News>> getAllNews() {
        return allNews;
    }

    LiveData<List<Reaction>> getAllReaction() {
        return allReaction;
    }

    LiveData<List<ReactionToNews>> getAllReactionToNews() {
        return allReactionToNews;
    }

    public void insert(News... news) {
        repository.insert(news);
    }

    public void insert(Reaction... reactions) {
        repository.insert(reactions);
    }

    public void insert(ReactionToNews... reactionToNews) {
        repository.insert(reactionToNews);
    }
}
