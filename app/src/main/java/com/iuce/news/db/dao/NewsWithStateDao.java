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
    @Query("SELECT * FROM " + News.TABLE_NAME)
    LiveData<List<NewsWithState>> getAllNewsWithState();

    // https://www.w3resource.com/sql/aggregate-functions/count-having.php
    @Query(
            "DELETE FROM "
                    + News.TABLE_NAME + " "
                    + "WHERE " + News.COLUMN_ID + " NOT IN ("
                    + "SELECT " + State.COLUMN_NEWS_ID + " FROM " + State.TABLE_NAME + " "
                    + "WHERE " + State.COLUMN_NAME + "= '" + State.NAME_FEED + "' "
                    + "GROUP BY " + State.COLUMN_NEWS_ID + " HAVING Count(*) = 1"
                    + ")"

    )
    void deleteOnlyFeed();

    /*@Transaction
    @TypeConverter
    @Query("SELECT * FROM " + News.TABLE_NAME + ", " + State.TABLE_NAME + " WHERE " + State.COLUMN_NAME +
            "= " + ":stateName")
    LiveData<List<NewsWithState>> getAllNewsWithStateByState(State stateName);

    @Query("SELECT " + State.COLUMN_NAME + " FROM " + News.TABLE_NAME + " WHERE " + News.COLUMN_ID + "= :nid")
    public LiveData<List<NewsWithState>> getNewsWithStateByNid(int nid);*/
}
