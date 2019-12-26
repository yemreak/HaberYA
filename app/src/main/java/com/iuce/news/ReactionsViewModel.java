package com.iuce.news;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReactionsViewModel extends AndroidViewModel {
    private ReactionsRepository repository;

    private LiveData<List<Reaction>> allReaction;

    public ReactionsViewModel(Application application) {
        super(application);
        repository = new ReactionsRepository(application);
        allReaction = repository.getAllReactions();
    }

    LiveData<List<Reaction>> getAllReaction() { return allReaction; }

    public void insert(Reaction... reactions) { repository.insert(reactions); }
}
