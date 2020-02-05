package com.yemreak.haberya;

import androidx.annotation.NonNull;

import com.yemreak.haberya.db.entity.News;

public class Globals {
	private static Globals INSTANCE;
	private News selectedNews;

	private Globals() {
	}

	public static synchronized Globals getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Globals();
		}
		return INSTANCE;
	}

	@NonNull
	public News getSelectedNews() throws NullPointerException {
		if (selectedNews == null) {
			throw new NullPointerException("The selected news invoked without creation");
		}
		return selectedNews;
	}

	public void setSelectedNews(@NonNull News news) {
		selectedNews = news;
	}
}
