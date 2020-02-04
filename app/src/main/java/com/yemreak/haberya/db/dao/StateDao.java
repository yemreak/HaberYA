package com.yemreak.haberya.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.yemreak.haberya.db.entity.State;

@Dao
public interface StateDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(State... state);

    @Delete
    void delete(State... state);
}
