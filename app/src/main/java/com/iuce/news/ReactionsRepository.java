package com.iuce.news;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Details: https://android.yemreak.com/veriler/room-database#repository-yapisi
 */
public class ReactionsRepository {

    private ReactionsDao reactionsDao;
    private LiveData<List<Reaction>> allReaction;

    ReactionsRepository(Application application) {
        ReactionsRoomDatabase db = ReactionsRoomDatabase.getDatabase(application);
        reactionsDao = db.reactionsDao();
        allReaction = reactionsDao.getAllReactions();
    }

    LiveData<List<Reaction>> getAllReactions() {
        return allReaction;
    }

    public void insert(Reaction... reactions) {
        new InsertAsyncTask(reactionsDao).execute(reactions);
    }


    private static class InsertAsyncTask extends AsyncTask<Reaction, Reaction, Void> {

        private ReactionsDao reactionsDao;

        InsertAsyncTask(ReactionsDao reactionsDao) {
            this.reactionsDao = reactionsDao;
        }

        @Override
        protected Void doInBackground(final Reaction... reactions) {
            for (Reaction reaction : reactions) {
                reactionsDao.insert(reaction);
            }
            return null;
        }
    }
}
