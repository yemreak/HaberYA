package com.iuce.news.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;

import java.util.List;


/**
 * @see <a href="https://developer.android.com/reference/androidx/room/Relation.html">Relation ~ Android</a>
 */
@Dao
public interface NewsWithStateDao {

    @Transaction
    @Query("SELECT * FROM " + News.TABLE_NAME + " ORDER BY " + News.COLUMN_ID + " DESC")
    LiveData<List<NewsWithState>> getAllNewsWithState();

    @Transaction
    @Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN (:ids)" + " ORDER BY " + News.COLUMN_ID + " DESC")
    LiveData<List<NewsWithState>> getNewsWithStateByIDs(Integer... ids);

    @Transaction
    @Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN ("
            + "SELECT " + State.COLUMN_NEWS_ID + " FROM " + State.TABLE_NAME + " WHERE "
            + State.COLUMN_TYPE + " = :stateType ) " + " ORDER BY " + News.COLUMN_ID + " DESC"
    )
    LiveData<List<NewsWithState>> getNewsWithStateByState(int stateType);

    @Transaction
    @Query("SElECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN ("
            + "SELECT " + State.COLUMN_NEWS_ID + " FROM " + State.TABLE_NAME + ")"
            + " ORDER BY " + News.COLUMN_ID + " DESC"
    )
    LiveData<List<NewsWithState>> getAllNewsWithStateHasStates();

    @Transaction
    @Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_CATEGORY + " IN (:categories)" + " ORDER BY " + News.COLUMN_ID + " DESC")
    LiveData<List<NewsWithState>> getByCategories(String... categories);

    @Transaction
    @Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_COUNTRY + " IN (:countries)" + " ORDER BY " + News.COLUMN_ID + " DESC")
    LiveData<List<NewsWithState>> getByCountries(String... countries);
}
