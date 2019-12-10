package com.iuce.news;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Details: https://android.yemreak.com/veriler/room-database#repository-yapisi
 */
public class NewsRepository {

    private NewsDao newsDao;
    private LiveData<List<News>> newsList;

    NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        newsDao = db.newsDao();
        newsList = newsDao.getAllNews();
    }

    LiveData<List<News>> getNewsList() {
        return newsList;
    }

    public void insert(News news) {
        new InsertAsyncTask(newsDao).execute(news);
    }

    private static class InsertAsyncTask extends AsyncTask<News, News, Void> {

        private NewsDao newsDao;

        InsertAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(final News... news) {
            for (News aNews : news) {
                newsDao.insert(aNews);
            }
            return null;
        }
    }
}
