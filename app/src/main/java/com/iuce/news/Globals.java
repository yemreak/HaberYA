package com.iuce.news;

import androidx.annotation.NonNull;

import com.iuce.news.db.entity.News;
import com.iuce.news.db.pojo.NewsWithState;

import java.util.List;

/**
 * Details: https://android.yemreak.com/temel/global-degiskenler
 */
public class Globals {

    private NewsWithState selectedNewsWithState;
    private Long[] newsIDList;

    private static Globals INSTANCE;

    private Globals() {
    }

    public static synchronized Globals getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Globals();
        }
        return INSTANCE;
    }

    public void setSelectedNewsWithState(@NonNull NewsWithState selectedNewsWithState) {
        this.selectedNewsWithState = selectedNewsWithState;
    }

    @NonNull
    public NewsWithState getSelectedNewsWithState() throws NullPointerException {
        if (selectedNewsWithState == null) {
            throw new NullPointerException("The selected news invoked without creation");
        }
        return selectedNewsWithState;
    }

    public Long[] getNewsIDList() {
        return newsIDList;
    }

    public void setNewsIDList(Long[] newsIDList) {
        this.newsIDList = newsIDList;
    }
}
