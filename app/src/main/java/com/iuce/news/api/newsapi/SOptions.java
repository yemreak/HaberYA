package com.iuce.news.api.newsapi;

public class SOptions extends BaseOptions {

    public static final String HEAD = "sources";

    SOptions(Builder builder) {
        super(builder);
    }

    public static Builder Builder() {
        return new Builder();
    }

    @Override
    public String buildUrl(String apiKey) {
        return super.buildUrl(HEAD, apiKey);
    }

    public static final class Builder extends BaseOptions.Builder {

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

        @Override
        public SOptions build() {
            return new SOptions(this);
        }
    }
}
