package com.iuce.news.api.newsapi;

import java.util.Date;

public class EOptions extends BaseOptions {

    public static final String HEAD = "everything";

    private String language;
    private String from;
    private String to;
    private String sortBy;

    EOptions(Builder builder) {
        super(builder);
        this.language = builder.language;
        this.from = builder.from;
        this.to = builder.to;
        this.sortBy = builder.sortBy;
    }

    public static SOptions.Builder Builder() {
        return new SOptions.Builder();
    }

    @Override
    public String buildUrl(String apiKey) {
        return super.buildUrl(HEAD, apiKey);
    }

    @Override
    String buildQueries() {
        return "" // 🦅
                + generateQuery("language", language)
                + generateQuery("from", from)
                + generateQuery("to", to)
                + generateQuery("sortBy", sortBy)
                + super.buildQueries();
    }

    public enum Language {

        AR, DE, EN, ES, FR, HE, IT, NL, NO, PT, RU, SE, UD, ZH;

        public String getValue() {
            return this.name().toLowerCase();
        }
    }

    public enum SortBy {

        RELEVANCY, POPULARITY, PUBLISHED_AT;

        public String getValue() {
            if (this == PUBLISHED_AT)
                return "publishedAt";
            return this.name().toLowerCase();
        }
    }

    public static final class Builder extends BaseOptions.Builder {

        private String language;
        private String from;
        private String to;
        private String sortBy;

        @Override
        public Builder setCategory(Category category) {
            this.category = category.getValue();
            return this;
        }

        @Override
        public Builder setSources(String sources) {
            this.sources = sources;
            return this;
        }

        @Override
        public Builder setQuery(String query) {
            this.query = query;
            return this;
        }

        @Override
        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        @Override
        public Builder setPage(int page) {
            this.page = page;
            return this;
        }

        public Builder setLanguage(Language language) {
            this.language = language.getValue();
            return this;
        }

        public Builder setFrom(Date from) {
            this.from = from.toString();
            return this;
        }

        public Builder setTo(Date to) {
            this.to = to.toString();
            return this;
        }

        public Builder setSortBy(SortBy sortBy) {
            this.sortBy = sortBy.getValue();
            return this;
        }

        @Override
        public EOptions build() {
            return new EOptions(this);
        }
    }
}
