package com.iuce.news.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.iuce.news.db.entity.State;

@Dao
public interface StateDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(State... state);
}
