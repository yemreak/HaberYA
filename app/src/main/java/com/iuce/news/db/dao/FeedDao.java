package com.iuce.news.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.iuce.news.db.entity.Feed;

@Dao
public interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Feed... feed);
}
