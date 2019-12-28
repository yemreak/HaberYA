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
    private ReactionDao reactionDao;
    private ReactionToNewsDao reactionToNewsDao;

    private LiveData<List<News>> allNews;
    private LiveData<List<Reaction>> allReaction;
    private LiveData<List<ReactionToNews>> allReactionToNews;

    NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        newsDao = db.newsDao();
        reactionDao = db.reactionsDao();
        reactionToNewsDao = db.reactionToNewsDao();

        allNews = newsDao.getAllNews();
        allReaction = reactionDao.getAllReactions();
        allReactionToNews = reactionToNewsDao.getAllReactionToNews();
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
        new InsertNewsAsyncTask(newsDao).execute(news);
    }

    public void insert(Reaction... reactions) {
        new InsertReactionAsyncTask(reactionDao).execute(reactions);
    }

    public void insert(ReactionToNews... reactionsToNews) {
        new InsertReactionToNewsAsyncTask(reactionToNewsDao).execute(reactionsToNews);
    }

    public void delete() {
        new DeleteAsyncTask(newsDao).execute();
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
    private static class InsertNewsAsyncTask extends AsyncTask<News, News, Void> {

        private NewsDao newsDao;

        InsertNewsAsyncTask(NewsDao newsDao) {
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

    private static class InsertReactionAsyncTask extends AsyncTask<Reaction, Reaction, Void> {

        private ReactionDao reactionDao;

        InsertReactionAsyncTask(ReactionDao reactionDao) {
            this.reactionDao = reactionDao;
        }

        @Override
        protected Void doInBackground(final Reaction... reactions) {
            for (Reaction reaction : reactions) {
                reactionDao.insert(reaction);
            }
            return null;
        }
    }

    private static class InsertReactionToNewsAsyncTask extends AsyncTask<ReactionToNews, ReactionToNews, Void> {

        private ReactionToNewsDao reactionToNewsDao;

        InsertReactionToNewsAsyncTask(ReactionToNewsDao reactionToNewsDao) {
            this.reactionToNewsDao = reactionToNewsDao;
        }

        @Override
        protected Void doInBackground(final ReactionToNews... reactionsToNews) {
            for (ReactionToNews reactionToNews : reactionsToNews) {
                reactionToNewsDao.insert(reactionToNews);
            }
            return null;
        }
    }
}
