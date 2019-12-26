package com.iuce.news;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.iuce.news.ReactionToNews.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class ReactionToNews {

    public static final String TABLE_NAME = "reactions_to_news";
    public static final String COLUMN_ID = "rtn_id";
    public static final String COLUMN_REACTION = "rid";
    public static final String COLUMN_NEWS = "nid";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = COLUMN_NEWS)
    private int niw;

    @ColumnInfo(name = COLUMN_REACTION)
    private int rid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNiw() {
        return niw;
    }

    public void setNiw(int niw) {
        this.niw = niw;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
