package com.iuce.news.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = Feed.TABLE_NAME, indices = {@Index(value = Feed.COLUMN_NEWS_ID, unique = true)})
public class Feed {

    public static final String TABLE_NAME = "feed";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NEWS_ID = "nid";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Feed.COLUMN_ID)
    private int id;

    @ForeignKey(
            entity = Feed.class,
            parentColumns = News.COLUMN_ID,
            childColumns = Feed.COLUMN_ID,
            onDelete = ForeignKey.CASCADE
    )
    @ColumnInfo(name = Feed.COLUMN_NEWS_ID)
    private long nid;

    public Feed(long nid) {
        this.nid = nid;
    }

    public static Feed[] Builder(Long[] newsIDs) {
        List<Feed> feedList = new ArrayList<>();
        for (Long nid : newsIDs) {
            if (nid > 0) {
                feedList.add(new Feed(nid));
            }
        }
        return feedList.toArray(new Feed[0]);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }
}
