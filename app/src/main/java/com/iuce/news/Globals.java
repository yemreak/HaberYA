package com.iuce.news;

import androidx.annotation.NonNull;

/**
 * Details: https://android.yemreak.com/temel/global-degiskenler
 */
class Globals {

    private News selectedNews;

    private static Globals INSTANCE;

    private Globals() {
    }

    static synchronized Globals getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Globals();
        }
        return INSTANCE;
    }

    void setSelectedNews(@NonNull News news) {
        selectedNews = news;
    }

    @NonNull
    News getSelectedNews() throws NullPointerException {
        if (selectedNews == null) {
            throw new NullPointerException("The selected news invoked without creation");
        }
        return selectedNews;
    }
}
