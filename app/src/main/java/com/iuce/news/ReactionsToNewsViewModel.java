package com.iuce.news;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReactionsToNewsViewModel extends AndroidViewModel {
    private ReactionsToNewsRepository repository;

    private LiveData<List<ReactionToNews>> allReactionToNews;

    public ReactionsToNewsViewModel(Application application) {
        super(application);
        repository = new ReactionsToNewsRepository(application);
        allReactionToNews = repository.getAllReactionToNews();
    }

    LiveData<List<ReactionToNews>> getAllReactionToNews() { return allReactionToNews; }

    public void insert(ReactionToNews... reactionsToNews) { repository.insert(reactionsToNews); }
}
