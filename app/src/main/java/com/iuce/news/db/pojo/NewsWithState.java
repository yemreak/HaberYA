package com.iuce.news.db.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.iuce.news.db.entity.News;
import com.iuce.news.db.entity.State;

import java.util.List;

/**
 * @see <a href="https://developer.android.com/reference/androidx/room/Relation.html">Relation ~ Android</a>
 */
public class NewsWithState {
    @Embedded
    private News news;

    @Relation(
            parentColumn = News.COLUMN_ID,
            entityColumn = State.COLUMN_NEWS_ID,
            entity = State.class
    )
    private List<State> states;

    public NewsWithState(News news, List<State> states) {
        this.news = news;
        this.states = states;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }
}