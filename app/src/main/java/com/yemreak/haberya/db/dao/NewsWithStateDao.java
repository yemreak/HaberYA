package com.yemreak.haberya.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.yemreak.haberya.db.entity.News;
import com.yemreak.haberya.db.entity.State;
import com.yemreak.haberya.db.pojo.NewsWithState;

import java.util.List;


/**
 * @see <a href="https://developer.android.com/reference/androidx/room/Relation.html">Relation ~ Android</a>
 */
@Dao
public interface NewsWithStateDao {

	@Transaction
	@Query("SELECT * FROM " + News.TABLE_NAME + " ORDER BY " + News.COLUMN_ID + " DESC")
	LiveData<List<NewsWithState>> getAll();

	@Query("SELECT Count(*) FROM " + News.TABLE_NAME)
	int countAll();

	@Query("SELECT Count(*) FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " NOT IN (" +
			"SELECT " + State.COLUMN_NEWS_ID + " FROM " + State.TABLE_NAME + " INNER JOIN " + News.TABLE_NAME +
			")")
	int countAllStateless();

	@Transaction
	@Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_TITLE + " LIKE :titleQuery" +
			" ORDER BY " + News.COLUMN_ID + " DESC")
	LiveData<List<NewsWithState>> getAllByTitle(String titleQuery);

	@Transaction
	@Query(""
			+ "SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_TITLE + " LIKE :titleQuery "
			+ "AND " + News.COLUMN_ID + " IN ( "
			+ "     SELECT " + State.COLUMN_NEWS_ID + " FROM " + State.TABLE_NAME
			+ "     WHERE " + State.COLUMN_TYPE + " IN (:types)"
			+ ")"
			+ " ORDER BY " + News.COLUMN_ID + " DESC")
	LiveData<List<NewsWithState>> getAllByTitleAndType(String titleQuery, Integer... types);

	@Transaction
	@Query("SElECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN ("
			+ "SELECT " + State.COLUMN_NEWS_ID + " FROM " + State.TABLE_NAME + ")"
			+ " ORDER BY " + News.COLUMN_ID + " DESC"
	)
	LiveData<List<NewsWithState>> getAllHasStates();

	@Transaction
	@Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN (:ids)" + " ORDER BY " + News.COLUMN_ID + " DESC")
	LiveData<List<NewsWithState>> getByIDs(Integer... ids);

	@Transaction
	@Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN ("
			+ "SELECT " + State.COLUMN_NEWS_ID + " FROM " + State.TABLE_NAME + " WHERE "
			+ State.COLUMN_TYPE + " IN (:stateType) ) " + " ORDER BY " + News.COLUMN_ID + " DESC"
	)
	LiveData<List<NewsWithState>> getByStates(Integer... stateType);

	@Transaction
	@Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_CATEGORY + " IN (:categories)" + " ORDER BY " + News.COLUMN_ID + " DESC")
	LiveData<List<NewsWithState>> getByCategories(String... categories);

	@Transaction
	@Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_COUNTRY + " IN (:countries)" + " ORDER BY " + News.COLUMN_ID + " DESC")
	LiveData<List<NewsWithState>> getByCountries(String... countries);

}
