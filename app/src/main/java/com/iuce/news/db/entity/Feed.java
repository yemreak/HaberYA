package com.iuce.news.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = Feed.TABLE_NAME, indices = {@Index(value = Feed.COLUMN_NEWS_ID, unique = true)})
public class Feed {

    public static final String TABLE_NAME = "feed";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NEWS_ID = "nid";

    @PrimaryKey
    @ColumnInfo(name = Feed.COLUMN_ID)
    private int id;

    @ForeignKey(
            entity = Feed.class,
            parentColumns = News.COLUMN_ID,
            childColumns = Feed.COLUMN_ID,
            onDelete = ForeignKey.CASCADE
    )
    @ColumnInfo(name = Feed.COLUMN_NEWS_ID)
    private int nid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }
}
