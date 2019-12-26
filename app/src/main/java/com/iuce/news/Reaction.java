package com.iuce.news;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.iuce.news.Reaction.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Reaction {

    public static final String TABLE_NAME = "reactions";
    public static final String COLUMN_ID = "rid";
    public static final String COLUMN_NAME = "name";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int rid;

    @ColumnInfo(name = COLUMN_NAME)
    private String name;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

