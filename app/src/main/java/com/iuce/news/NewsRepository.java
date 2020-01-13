package com.iuce.news;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.iuce.news.api.NewsAPIOptions;
import com.iuce.news.db.NewsRoomDatabase;
import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;

import java.util.ArrayList;
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

        allNewsWithState = db.newsWithStateDao().getAll();
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

    public LiveData<List<NewsWithState>> getNewsWithStateByStates(State.Type... types) {
        List<Integer> typeList = new ArrayList<>();
        for (State.Type type : types) {
            typeList.add(type.getId());
        }

        return db.newsWithStateDao().getByStates(typeList.toArray(new Integer[0]));
    }

    public LiveData<List<NewsWithState>> getAllNewsWithStateHasStates() {
        return db.newsWithStateDao().getAllHasStates();
    }


    public LiveData<List<NewsWithState>> getAllNewsWithState() {
        return allNewsWithState;
    }

    public LiveData<List<NewsWithState>> getNewsWithStateByIDs(Integer... stateIds) {
        return db.newsWithStateDao().getByIDs(stateIds);
    }

    public LiveData<List<NewsWithState>> getNewsWithStateByCountries(NewsAPIOptions.Country... countries) {
        List<String> countryList = new ArrayList<>();
        for (NewsAPIOptions.Country country : countries) {
            countryList.add(country.getValue());
        }

        return db.newsWithStateDao().getByCountries(countryList.toArray(new String[0]));
    }

    public LiveData<List<NewsWithState>> getNewsWithStateByCategories(NewsAPIOptions.Category... categories) {
        List<String> categoryList = new ArrayList<>();
        for (NewsAPIOptions.Category category : categories) {
            categoryList.add(category.getValue());
        }

        return db.newsWithStateDao().getByCategories(categoryList.toArray(new String[0]));
    }

    public void deleteRow(int rowCount) {
        doInBackground(() -> db.newsDao().deleteRow(rowCount));
    }

    public void insertNews(News... news) {
        doInBackground(() -> db.newsDao().insert(news));
    }

    public void insertStates(State... states) {
        doInBackground(() -> db.stateDao().insert(states));
    }

    public void deleteStates(State... states) {
        doInBackground(() -> db.stateDao().delete(states));
    }

    public void deleteNewsByIDList(Long... ids) {
        doInBackground(() -> db.newsDao().deleteByIDs(ids));
    }

    private void doInBackground(DaoAsyncTask.BackgroundTaskInterface backgroundTaskInterface) {
        new DaoAsyncTask(backgroundTaskInterface).execute();
    }

    private static final class DaoAsyncTask extends AsyncTask<Void, Void, Void> {

        private BackgroundTaskInterface backgroundTaskInterface;

        public interface BackgroundTaskInterface {
            void doInBackground();
        }

        public DaoAsyncTask(BackgroundTaskInterface backgroundTaskInterface) {
            this.backgroundTaskInterface = backgroundTaskInterface;
        }

        @Override
        protected final Void doInBackground(Void... voids) {
            backgroundTaskInterface.doInBackground();
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
