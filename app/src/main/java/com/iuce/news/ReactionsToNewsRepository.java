package com.iuce.news;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Details: https://android.yemreak.com/veriler/room-database#repository-yapisi
 */
public class ReactionsToNewsRepository {

    private ReactionsToNewsDao reactionsToNewsDao;
    private LiveData<List<ReactionToNews>> allReactionToNews;

    ReactionsToNewsRepository(Application application) {
        ReactionsToNewsRoomDatabase db = ReactionsToNewsRoomDatabase.getDatabase(application);
        reactionsToNewsDao = db.reactionToNewsDao();
        allReactionToNews = reactionsToNewsDao.getAllReactionToNews();
    }

    LiveData<List<ReactionToNews>> getAllReactionToNews() {
        return allReactionToNews;
    }

    public void insert(ReactionToNews... reactionsToNews) {
        new InsertAsyncTask(reactionsToNewsDao).execute(reactionsToNews);
    }


    private static class InsertAsyncTask extends AsyncTask<ReactionToNews, ReactionToNews, Void> {

        private ReactionsToNewsDao reactionsToNewsDao;

        InsertAsyncTask(ReactionsToNewsDao reactionsToNewsDao) {
            this.reactionsToNewsDao = reactionsToNewsDao;
        }

        @Override
        protected Void doInBackground(final ReactionToNews... reactionsToNews) {
            for (ReactionToNews reactionToNews : reactionsToNews) {
                reactionsToNewsDao.insert(reactionToNews);
            }
            return null;
        }
    }
}
