package com.iuce.news.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iuce.news.db.entity.News;

import java.util.List;

/**
 * Details: https://android.yemreak.com/veriler/room-database#dao-yapisi
 */
@Dao
public interface NewsDao {

    /**
     * Aynı URL değerine sahip haberleri eklemez (atlar)
     * @param news Haber objesi {@link News}
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(News news);

    // Update multiple entries with one call.
    @Update
    void updateNews(News... news);

    // Simple query that does not take parameters and returns nothing.
    @Query("DELETE FROM " + News.TABLE_NAME)
    void deleteAll();

    // Simple query without parameters that returns values.
    @Query("SELECT * from " + News.TABLE_NAME + " ORDER BY " + News.COLUMN_ID + " ASC")
    LiveData<List<News>> getAllNews();

    // Query with parameter that returns a specific news or news.
    @Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_TITLE + " LIKE :" + News.COLUMN_TITLE + " ")
    LiveData<List<News>> findNewsByTitle(String title);
}
