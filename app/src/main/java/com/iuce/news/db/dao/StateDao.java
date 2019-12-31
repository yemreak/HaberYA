package com.iuce.news.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iuce.news.db.entity.State;

@Dao
public interface StateDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(State... state);

    @Query("SELECT * FROM " + State.TABLE_NAME + " WHERE " + State.COLUMN_ID + " = :sid")
    int getState(int sid);
}
