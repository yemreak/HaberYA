package com.iuce.news.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @see <a href="https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a"> Foreign Key</a>
 */
@Entity(tableName = State.TABLE_NAME, indices = {@Index(value = {State.COLUMN_TYPE,
        State.COLUMN_NEWS_ID}, unique = true)})

public class State {

    public static final String TABLE_NAME = "states";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NEWS_ID = "nid";
    public static final String COLUMN_TYPE = "type";

    public static final int TYPE_READ = 1;
    public static final int TYPE_LIKED = 2;
    public static final int TYPE_LATER = 3;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private long id;

    @ForeignKey(
            entity = News.class,
            parentColumns = News.COLUMN_ID,
            childColumns = State.COLUMN_NEWS_ID,
            onDelete = ForeignKey.CASCADE
    )
    @ColumnInfo(name = COLUMN_NEWS_ID)
    private long nid;

    @ColumnInfo(name = COLUMN_TYPE)
    private long type;

    public State() {
    }

    @Ignore
    public State(long nid, long type) {
        this.nid = nid;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNid() {
        return nid;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    @Ignore
    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", nid=" + nid +
                ", type=" + type +
                '}';
    }
}
