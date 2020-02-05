package com.yemreak.haberya.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yemreak.haberya.db.entity.News;
import com.yemreak.haberya.db.entity.State;

/**
 * Details: https://android.yemreak.com/veriler/room-database#dao-yapisi
 */
@Dao
public interface NewsDao {

	/**
	 * Aynı URL değerine sahip haberleri eklemez (atlar)
	 *
	 * @param news Haber objesi {@link News}
	 * @return Eklenen haberlerin ID'si @see <a href="https://stackoverflow.com/a/44364516/9770490">Android Room - Get the id of new inserted row with auto-generate</a>
	 */
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	Long[] insert(News... news);

	@Query("DELETE FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN (:idList)")
	void deleteByIDs(Long... idList);

	@Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN (:ids)")
	LiveData<News> getByIDs(Long... ids);

	@Query("SELECT Count(*) FROM " + News.TABLE_NAME)
	int countAll();

	/**
	 * Haberler tablosundan ilk eklenen kayıtları silme
	 *
	 * @param rowCount Silenecek satır sayısı
	 * @see <a href="https://stackoverflow.com/a/9800582/9770490">Delete first N rows in android sqlite database</a>
	 */
	@Query("DELETE FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN ("
			+ "SELECT " + News.COLUMN_ID + " FROM " + News.TABLE_NAME + " WHERE NOT EXISTS ("
			+ "SELECT * FROM " + State.TABLE_NAME + " WHERE " + State.COLUMN_NEWS_ID + " = "
			+ News.COLUMN_ID + ") "
			+ " ORDER BY " + News.COLUMN_ID + " ASC LIMIT :rowCount )"
	)
	void deleteRow(int rowCount);
}
