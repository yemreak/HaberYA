package com.iuce.news;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.iuce.news.db.dao.NewsDao;
import com.iuce.news.db.dao.NewsWithStateDao;
import com.iuce.news.db.dao.StateDao;
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;

/**
 * Details: https://android.yemreak.com/veriler/room-database#repository-yapisi
 */
public class NewsRepository {

    public static final String TAG = "NewsRepository";

    private NewsDao newsDao;
    private NewsWithStateDao newsWithStateDao;
    private StateDao stateDao;

    private LiveData<List<News>> allNews;
    private LiveData<List<NewsWithState>> allNewsWithState;

    public NewsRepository(Application application) {
        com.iuce.news.db.NewsRoomDatabase db = com.iuce.news.db.NewsRoomDatabase.getDatabase(application);

        newsDao = db.newsDao();
        newsWithStateDao = db.newsWithStateDao();
        stateDao = db.stateDao();

        allNews = newsDao.getAllNews();
        allNewsWithState = newsWithStateDao.getAllNewsWithState();
    }

    public LiveData<List<NewsWithState>> getAllNewsWithState() {
        return allNewsWithState;
    }

    public void insertNews(News... news) {
        new InsertNewsAsyncTask(newsDao).execute(news);
    }

    public void insertState(State... states) {
        new InsertStateAsyncTask(stateDao).execute(states);
    }

    public void insertFeedNews(News... news) {
        new InsertFeedNews(newsDao, stateDao).execute(news);
    }

    public void delete() {
        new DeleteAsyncTask(newsDao).execute();
    }

    public void deleteOnlyFeed() {
        new DeleteOnlyFeedAsyncTask(newsWithStateDao).execute();
    }

    private static class DeleteOnlyFeedAsyncTask extends AsyncTask<NewsWithState, Void, Void> {

        private NewsWithStateDao newsWithStateDao;

        DeleteOnlyFeedAsyncTask(NewsWithStateDao newsWithStateDao) {
            this.newsWithStateDao = newsWithStateDao;
        }

        @Override
        protected Void doInBackground(final NewsWithState... newsWithStates) {
            newsWithStateDao.deleteOnlyFeed();
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<News, Void, Void> {

        private NewsDao newsDao;

        DeleteAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(final News... news) {
            newsDao.deleteAll();
            return null;
        }
    }

    private static class InsertFeedNews extends AsyncTask<News, Void, Void> {

        private NewsDao newsDao;
        private StateDao stateDao;

        public InsertFeedNews(NewsDao newsDao, StateDao stateDao) {
            this.newsDao = newsDao;
            this.stateDao = stateDao;
        }

        @Override
        protected Void doInBackground(final News... news) {
            ArrayList<State> stateList = new ArrayList<>();

            newsDao.insert(news);
            // TODO: Sorunlu, verileri bulamıyor. null döndürüyor
            for (News aNews : Objects.requireNonNull(newsDao.getAllNews().getValue())) {
                stateList.add(
                        new State(
                        aNews.getId(),
                        State.NAME_FEED
                ));
            }

            stateDao.insert(stateList.toArray(new State[0]));
            return null;
        }
    }

    private static class InsertNewsAsyncTask extends AsyncTask<News, Void, Void> {

        private NewsDao newsDao;

        InsertNewsAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(final News... news) {
            newsDao.insert(news);
            return null;
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
}
