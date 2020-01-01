package com.iuce.news;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.iuce.news.db.NewsRoomDatabase;
import com.iuce.news.db.dao.NewsDao;
import com.iuce.news.db.dao.NewsWithStateDao;
import com.iuce.news.db.dao.StateDao;
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;

import java.util.List;

/**
 * Details: https://android.yemreak.com/veriler/room-database#repository-yapisi
 */
public class NewsRepository {

    public static final String TAG = "NewsRepository";

    private NewsDao newsDao;
    private NewsWithStateDao newsWithStateDao;
    private StateDao stateDao;

    private LiveData<List<NewsWithState>> allNewsWithState;

    public NewsRepository(Application application) {
        com.iuce.news.db.NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);

        newsDao = db.newsDao();
        newsWithStateDao = db.newsWithStateDao();
        stateDao = db.stateDao();

        allNewsWithState = newsWithStateDao.getAllNewsWithState();
    }

    public LiveData<List<NewsWithState>> getAllNewsWithState() {
        return allNewsWithState;
    }

/*

    public LiveData<List<NewsWithState>> getNewsWithStateByIDs(Long... ids) {
       new RepositoryAsyncTask<Long, Void, LiveData<List<NewsWithState>>>(
               longs -> newsWithStateDao.getNewsWithStateByIDs(longs),
               listLiveData -> Log.i("sad", "s")
       ).execute(ids);
    }
*/

    public void insertState(State... states) {
        new InsertStateAsyncTask(stateDao).execute(states);
    }

    public void deleteNewsByIDList(Long... ids) {
        new RepositoryAsyncTask<Long, Void>(
                longs -> newsDao.deleteByIDs(ids),
                null
        ).execute(ids);
    }

    public void insertNews(News... news) {
        new RepositoryAsyncTask<News, Long[]>(
                iNews -> {
                    newsDao.insert(iNews);
                    return null;
                },
                longs -> Globals.getInstance().setNewsIDList(longs)
        ).execute(news);
    }

    private static class InsertNewsAsyncTask extends AsyncTask<News, Void, Long[]> {

        private NewsDao newsDao;

        public InsertNewsAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Long[] doInBackground(News... news) {
            return newsDao.insert(news);
        }

        @Override
        protected void onPostExecute(Long[] ids) {
            Globals.getInstance().setNewsIDList(ids);
        }
    }

    private static class RepositoryAsyncTask<Params> extends AsyncTask<Params, Void,
            Void> {

        private BackgroundTaskInterface<Params> backgroundTaskInterface;

        public interface BackgroundTaskInterface<Params> {
            void doInBackground(Params... params);
        }

        public RepositoryAsyncTask(BackgroundTaskInterface<Params> backgroundTaskInterface) {
            this.backgroundTaskInterface = backgroundTaskInterface;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(Params... params) {
            backgroundTaskInterface.doInBackground(params);
            return null;
        }
    }

    private static class RepositoryAsyncTask<Params, Results> extends AsyncTask<Params, Void, Results> {

        private BackgroundTaskInterface<Params, Results> backgroundTaskInterface;
        private PostExecuteInterface<Results> postExecuteInterface;

        public interface BackgroundTaskInterface<Params, Results> {
            Results doInBackground(Params... params);
        }


        public interface PostExecuteInterface<Results> {
            void onPostExecute(Results results);
        }

        public RepositoryAsyncTask(BackgroundTaskInterface<Params, Results> backgroundTaskInterface,
                                   PostExecuteInterface<Results> postExecuteInterface) {
            this.backgroundTaskInterface = backgroundTaskInterface;
            this.postExecuteInterface = postExecuteInterface;
        }

        @SafeVarargs
        @Override
        protected final Results doInBackground(Params... params) {
            return backgroundTaskInterface.doInBackground(params);
        }

        @Override
        protected void onPostExecute(Results results) {
            postExecuteInterface.onPostExecute(results);
        }
    }

    private static class InsertStateAsyncTask extends AsyncTask<State, Void, Void> {

        private StateDao stateDao;

        InsertStateAsyncTask(StateDao stateDao) {
            this.stateDao = stateDao;
        }

        @Override
        protected Void doInBackground(@NonNull final State... states) {
            stateDao.insert(states);

            for (State state : states) {
                Log.d(TAG, "InsertStateAsyncTask: " + state);
            }
            return null;
        }
    }

    private static class DeleteNewsByIDListAsyncTask extends AsyncTask<Long, Void, Void> {

        private NewsDao newsDao;

        public DeleteNewsByIDListAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(Long... ids) {
            newsDao.deleteByIDs(ids);
            return null;
        }
    }
}
