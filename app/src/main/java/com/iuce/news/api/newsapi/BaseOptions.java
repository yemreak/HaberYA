package com.iuce.news.api.newsapi;

public abstract class BaseOptions {

    private static final String URL_TEMPLATE = "https://newsapi.org/v2/%s?%s&apiKey=%s";

    private String category;
    private String sources;
    private String query;

    private int pageSize;
    private int page;

    BaseOptions(Builder builder) {
        this.category = builder.category;
        this.sources = builder.sources;
        this.query = builder.query;
        this.pageSize = builder.pageSize;
        this.page = builder.page;
    }

    abstract public String buildUrl(String apiKey);

    String buildUrl(String head, String apiKey) {
        return String.format(URL_TEMPLATE, head, buildQueries(), apiKey);
    }

    String buildQueries() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(generateQuery("category", category));
        stringBuilder.append(generateQuery("sources", sources));
        stringBuilder.append(generateQuery("q", query));
        stringBuilder.append(generateQuery("pageSize", pageSize));
        stringBuilder.append(generateQuery("page", page));
        stringBuilder = trimQuery(stringBuilder);

        return stringBuilder.toString();
    }

    String generateQuery(String name, String value) {
        if (value == null)
            return "";
        return name + "=" + value + "&";
    }

    String generateQuery(String name, int value) {
        if (value == -1)
            return "";
        return name + "=" + value + "&";
    }

    StringBuilder trimQuery(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0)
            return stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
        return stringBuilder;
    }

    public String getCategory() {
        return category;
    }

    public enum Category {

        ANY, BUSINESS, ENTERTAINMENT, GENERAL, HEALTH, SCIENCE, SPORTS, TECHNOLOGY;

        public String getValue() {
            return this.name().toLowerCase();
        }
    }

    public static abstract class Builder {

        protected String category;
        protected String sources;
        protected String query;

        protected int pageSize = -1;
        protected int page = -1;

        abstract public Builder setCategory(Category category);

        abstract public Builder setSources(String sources);

        abstract public Builder setQuery(String query);

        abstract public Builder setPageSize(int pageSize);

        abstract public Builder setPage(int page);

        public abstract BaseOptions build();
    }
}
