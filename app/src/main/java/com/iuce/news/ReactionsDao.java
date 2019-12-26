package com.iuce.news;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReactionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reaction reaction);

    @Update
    void updateReactions(Reaction... reactions);

    @Query("DELETE FROM " + Reaction.TABLE_NAME)
    void deleteAll();

    @Query("SELECT * FROM " + Reaction.TABLE_NAME + " ORDER BY " + Reaction.COLUMN_ID + " ASC")
    LiveData<List<Reaction>> getAllReactions();

    @Query("SELECT * FROM " + Reaction.TABLE_NAME + " WHERE " + Reaction.COLUMN_NAME + " LIKE :" + Reaction.COLUMN_NAME)
    LiveData<List<Reaction>> findReactionsByName(String name);

}
