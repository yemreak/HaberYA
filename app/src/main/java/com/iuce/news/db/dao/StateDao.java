package com.iuce.news.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.iuce.news.db.entity.State;

/**
 * Özel durum notları için kullaınılabilir
 */
@Dao
public interface StateDao {
    @Insert
    void insert(State... state);
}
