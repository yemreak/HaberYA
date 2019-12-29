package com.iuce.news.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @see <a href="https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a>Room Relarionships</a>
 * <a href="https://developer.android.com/reference/androidx/room/ForeignKey#childColumns()">Foreign Key</a>
 */
@Entity(
        tableName = State.TABLE_NAME,
        indices = {
                @Index(
                        value = {News.COLUMN_ID, State.COLUMN_NAME},
                        unique = true
                )
        })
public class State {

    public static final String TABLE_NAME = "states";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NEWS_ID = "nid";
    public static final String COLUMN_NAME = "name";

    public static final String NAME_FEED = "feed";
    public static final String NAME_LIKED = "liked";
    public static final String NAME_READ = "read";

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

    @ColumnInfo(name = COLUMN_NAME)
    private String name;

    public State(int nid, String name) {
        this.nid = nid;
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", nid=" + nid +
                ", name='" + name + '\'' +
                '}';
    }
}
