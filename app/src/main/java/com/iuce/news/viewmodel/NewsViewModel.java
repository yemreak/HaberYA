package com.iuce.news.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iuce.news.NewsRepository;
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.Reaction;
import com.iuce.news.db.entity.ReactionToNews;

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

    public LiveData<List<News>> getAllNews() {
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

    public void delete() {
        repository.delete();
    }
}
