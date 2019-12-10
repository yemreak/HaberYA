package com.iuce.news;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Details: https://android.yemreak.com/veriler/room-database#dao-yapisi
 */
@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News news);

    // Update multiple entries with one call.
    @Update
    void updateNewss(News... news);

    // Simple query that does not take parameters and returns nothing.
    @Query("DELETE FROM news_table")
    void deleteAll();

    // Simple query without parameters that returns values.
    @Query("SELECT * from news_table ORDER BY nid ASC")
    LiveData<List<News>> getAllNews();

    // Query with parameter that returns a specific news or news.
    @Query("SELECT * FROM news_table WHERE title LIKE :title ")
    LiveData<List<News>> findNewsByTitle(String title);
}
