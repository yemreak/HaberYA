package com.iuce.news;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.iuce.news.db.NewsRoomDatabase;
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;

import java.util.List;

/**
 * @see <a href="https://android.yemreak.com/veriler/room-database#repository-yapisi">Repository Yapısı ~ YEmreAk</a>
 * @see <a href="https://github.com/android/architecture-components-samples/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/DataRepository.java#L21">RoomDB Example ~ Android Dev</a>
 */
public class NewsRepository {

    public static final String TAG = "NewsRepository";

    private static NewsRoomDatabase db;
    private static NewsRepository instance;

    private LiveData<List<NewsWithState>> allNewsWithState;

    private NewsRepository(Application application) {
        db = NewsRoomDatabase.getDatabase(application);

        allNewsWithState = db.newsWithStateDao().getAllNewsWithState();
    }

    public static NewsRepository getInstance(final Application application) {
        if (instance == null) {
            synchronized (NewsRepository.class) {
                if (instance == null) {
                    instance = new NewsRepository(application);
                }
            }
        }
        return instance;
    }

    public LiveData<List<NewsWithState>> getAllNewsWithState() {
        return allNewsWithState;
    }

    public void getNewsByIDs(Long... ids) {
        // TODO: Shared mı yoksa roomDB mi karar verilmeli
    }

    public void insertState(State... states) {
        new DaoAsyncTask<State>(
                iStates -> db.stateDao().insert(states)
        ).execute(states);
    }

    public void deleteNewsByIDList(Long... ids) {
        new DaoAsyncTask<Long>(
                longs -> db.newsDao().deleteByIDs(ids)
        ).execute(ids);
    }

    public void insertNews(News... news) {
        new DaoWithResultAsyncTask<News, Long[]>(
                iNews -> db.newsDao().insert(iNews),
                longs -> Globals.getInstance().setNewsIDList(longs)
        ).execute(news);
    }

    private static final class DaoAsyncTask<Params> extends AsyncTask<Params, Void,
            Void> {

        private BackgroundTaskInterface<Params> backgroundTaskInterface;

        public interface BackgroundTaskInterface<Params> {
            void doInBackground(Params... params);
        }

        public DaoAsyncTask(BackgroundTaskInterface<Params> backgroundTaskInterface) {
            this.backgroundTaskInterface = backgroundTaskInterface;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(Params... params) {
            backgroundTaskInterface.doInBackground(params);
            return null;
        }
    }

    private static final class DaoWithResultAsyncTask<Params, Results> extends AsyncTask<Params,
            Void, Results> {

        private BackgroundTaskInterface<Params, Results> backgroundTaskInterface;
        private PostExecuteInterface<Results> postExecuteInterface;

        public interface BackgroundTaskInterface<Params, Results> {
            Results doInBackground(Params... params);
        }

        public interface PostExecuteInterface<Results> {
            void onPostExecute(Results results);
        }

        public DaoWithResultAsyncTask(BackgroundTaskInterface<Params, Results> backgroundTaskInterface,
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
}
