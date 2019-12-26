package com.iuce.news;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReactionsToNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ReactionToNews rtn);

    @Update
    void updateReactionToNews(ReactionToNews... reactionsToNews);

    @Query("SELECT * FROM " + ReactionToNews.TABLE_NAME)
    LiveData<List<ReactionToNews>> getAllReactionToNews();

    @Query("DELETE FROM " + ReactionToNews.TABLE_NAME)
    void deleteAll();

    @Query("DELETE FROM " + ReactionToNews.TABLE_NAME + " WHERE " + ReactionToNews.COLUMN_REACTION
            + " = :rid" + " AND " + ReactionToNews.COLUMN_NEWS + " = :nid")
    void deleteReactionToNews(int rid, int nid);

    @Query("SELECT * FROM " + ReactionToNews.TABLE_NAME + " WHERE " + ReactionToNews.COLUMN_NEWS
            + " = :nid")
    LiveData<List<ReactionToNews>> findReactionsByNews(int nid);
}
