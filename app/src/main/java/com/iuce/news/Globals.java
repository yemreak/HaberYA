package com.iuce.news;

import androidx.annotation.NonNull;

import com.iuce.news.db.entity.News;

/**
 * Details: https://android.yemreak.com/temel/global-degiskenler
 */
public class Globals {

    private News selectedNews;

    private static Globals INSTANCE;

    private Globals() {
    }

    public static synchronized Globals getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Globals();
        }
        return INSTANCE;
    }

    public void setSelectedNews(@NonNull News news) {
        selectedNews = news;
    }

    @NonNull
    public News getSelectedNews() throws NullPointerException {
        if (selectedNews == null) {
            throw new NullPointerException("The selected news invoked without creation");
        }
        return selectedNews;
    }
}
