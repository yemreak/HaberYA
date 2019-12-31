package com.iuce.news.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * @see <a href="https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a>Room Relarionships</a>
 * <a href="https://developer.android.com/reference/androidx/room/ForeignKey#childColumns()">Foreign Key</a>
 */
@Entity(tableName = State.TABLE_NAME)

public class State {

    public static final String TABLE_NAME = "states";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NEWS_ID = "nid";
    public static final String COLUMN_TYPE = "type";

    public static final int NAME_READ = 1;
    public static final int NAME_LIKED = 2;
    public static final int NAME_LATER = 3;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int id;

    @ForeignKey(
            entity = News.class,
            parentColumns = News.COLUMN_ID,
            childColumns = State.COLUMN_NEWS_ID,
            onDelete = ForeignKey.CASCADE
    )
    @ColumnInfo(name = COLUMN_NEWS_ID)
    private int nid;

    @ColumnInfo(name = COLUMN_TYPE)
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", nid=" + nid +
                ", type=" + type +
                '}';
    }
}
