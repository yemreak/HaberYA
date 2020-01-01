package com.iuce.news.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.iuce.news.db.entity.Feed;
import com.iuce.news.db.entity.News;

import java.util.List;

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

    @Query("SELECT * FROM "
            + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN ("
            + "SELECT " + Feed.COLUMN_NEWS_ID + " FROM " + Feed.TABLE_NAME
            + ")"
    )
    LiveData<News> getAllFeed();

    @Query("DELETE FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " IN (" +
            "SELECT " + Feed.COLUMN_NEWS_ID + " FROM " + Feed.TABLE_NAME
            + " )")
    void deleteAllFeed();

    /*// Simple query that does not take parameters and returns nothing.
    @Query("DELETE FROM " + News.TABLE_NAME)
    void deleteAll();*/

    // Simple query without parameters that returns values.
    @Query("SELECT * from " + News.TABLE_NAME + " ORDER BY " + News.COLUMN_ID + " ASC")
    LiveData<List<News>> getAll();

    @Query("DELETE FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + " = :id")
    void delete(int id);

    // Query with parameter that returns a specific news or news.
    @Query("SELECT * FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_TITLE + " LIKE :" + News.COLUMN_TITLE + " ")
    LiveData<List<News>> findByTitle(String title);
}
