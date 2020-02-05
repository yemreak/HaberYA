package com.yemreak.haberya.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.yemreak.haberya.db.pojo.NewsWithState;

import java.util.List;

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
	public static State Builder(NewsWithState newsWithState, Type type) {
		return Builder(newsWithState.getNews(), type);
	}

	@Ignore
	public static State Builder(News news, Type type) {
		State state = new State();
		state.nid = news.getId();
		state.type = type.getId();

		return state;
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

	public enum Type {

		READ, LIKED, LATER;

		static {
			READ.id = 1;
			LIKED.id = 2;
			LATER.id = 3;
		}

		private int id;

		public int getId() {
			return this.id;
		}

		public State findState(List<State> stateList) {
			for (State state : stateList) {
				if (state.getType() == this.getId()) {
					return state;
				}
			}
			return null;
		}

		public boolean isExist(List<State> stateList) {
			return findState(stateList) != null;
		}

		@NonNull
		@Override
		public String toString() {
			return String.valueOf(this.getId());
		}
	}
}
