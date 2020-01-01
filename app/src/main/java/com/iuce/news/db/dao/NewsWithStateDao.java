package com.iuce.news.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.iuce.news.db.entity.News;
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
    @Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN (:ids)")
    LiveData<List<NewsWithState>> getNewsWithStateByIDs(Long... ids);

}
