package com.yemreak.haberya;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.yemreak.haberya.api.newsapi.Options;
import com.yemreak.haberya.api.newsapi.THOptions;
import com.yemreak.haberya.db.NewsRoomDatabase;
import com.yemreak.haberya.db.entity.News;
import com.yemreak.haberya.db.entity.State;
import com.yemreak.haberya.db.pojo.NewsWithState;

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

	public LiveData<List<NewsWithState>> getNewsWithStateByCountries(THOptions.Country... countries) {
		List<String> countryList = new ArrayList<>();
		for (THOptions.Country country : countries) {
			countryList.add(country.getValue());
		}

		return db.newsWithStateDao().getByCountries(countryList.toArray(new String[0]));
	}

	public LiveData<List<NewsWithState>> getNewsWithStateByCategories(Options.Category... categories) {
		List<String> categoryList = new ArrayList<>();
		for (Options.Category category : categories) {
			categoryList.add(category.getValue());
		}

		return db.newsWithStateDao().getByCategories(categoryList.toArray(new String[0]));
	}

	public LiveData<List<NewsWithState>> getAllNewsWithStateByTitle(String title) {
		return db.newsWithStateDao().getAllByTitle("%" + title + "%");
	}

	public LiveData<List<NewsWithState>> getAllNewsWithStateByTitleAndTypes(String title, State.Type... types) {
		List<Integer> typeList = new ArrayList<>();
		for (State.Type stateType : types) {
			typeList.add(stateType.getId());
		}

		return db.newsWithStateDao().getAllByTitleAndType("%" + title + "%", typeList.toArray(new Integer[0]));
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

	public void countAllNews(ResultListener resultListener) {
		doInBackground(() -> resultListener.onResult(db.newsDao().countAll()));
	}

	public void countAllNewsWithState(ResultListener resultListener) {
		doInBackground(() -> resultListener.onResult(db.newsWithStateDao().countAll()));
	}

	public void countAllStatelessNews(ResultListener resultListener) {
		doInBackground(() -> resultListener.onResult(db.newsWithStateDao().countAllStateless()));
	}

	private void doInBackground(DaoAsyncTask.BackgroundTaskInterface backgroundTaskInterface) {
		new DaoAsyncTask(backgroundTaskInterface).execute();
	}

	public interface ResultListener {
		void onResult(int result);
	}

	private static final class DaoAsyncTask extends AsyncTask<Void, Void, Void> {

		private BackgroundTaskInterface backgroundTaskInterface;

		public DaoAsyncTask(BackgroundTaskInterface backgroundTaskInterface) {
			this.backgroundTaskInterface = backgroundTaskInterface;
		}

		@Override
		protected final Void doInBackground(Void... voids) {
			backgroundTaskInterface.doInBackground();
			return null;
		}

		public interface BackgroundTaskInterface {
			void doInBackground();
		}
	}

	private static final class DaoWithResultAsyncTask<Params, Results> extends AsyncTask<Params,
			Void, Results> {

		private BackgroundTaskInterface<Params, Results> backgroundTaskInterface;
		private PostExecuteInterface<Results> postExecuteInterface;

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

		public interface BackgroundTaskInterface<Params, Results> {
			Results doInBackground(Params... params);
		}

		public interface PostExecuteInterface<Results> {
			void onPostExecute(Results results);
		}
	}
}
