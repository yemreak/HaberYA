package com.iuce.news.api.newsapi;


/**
 * Top Headlines işlemleri için gerekli ayarlar
 *
 * @see <a href="https://newsapi.org/docs/endpoints/top-headlines">Top Headlines ~ NewsAPI</a>
 */
public class THOptions extends Options {

    public static final String HEAD = "top-headlines";

    private Country country;
    private Category category;

    private String sources;
    private String query;

    private int pageSize;
    private int page;

    THOptions(Builder builder) {
        this.country = builder.country;
        this.category = builder.category;
        this.sources = builder.sources;
        this.query = builder.query;
        this.pageSize = builder.pageSize;
        this.page = builder.page;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public String buildUrl(String apiKey) {
        return super.buildUrl(HEAD, buildQueries(), apiKey);
    }

    private String buildQueries() {
        return trimQuery("" // 🦅
                + generateQuery("country", country)
                + generateQuery("category", category)
                + generateQuery("sources", sources)
                + generateQuery("q", query)
                + generateQuery("pageSize", pageSize)
                + generateQuery("page", page)
        );
    }

    public Country getCountry() {
        return country;
    }

    public Category getCategory() {
        return category;
    }

    @SuppressWarnings("unused")
    public String getSources() {
        return sources;
    }

    @SuppressWarnings("unused")
    public String getQuery() {
        return query;
    }

    @SuppressWarnings("unused")
    public int getPageSize() {
        return pageSize;
    }

    @SuppressWarnings("unused")
    public int getPage() {
        return page;
    }

    @SuppressWarnings("unused")
    public static final class Builder {

        private Country country;
        private Category category;

        private String sources;
        private String query;

        private int pageSize;
        private int page;

        public Builder setCountry(Country country) {
            this.country = country;
            return this;
        }

        public Builder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder setSources(String sources) {
            this.sources = sources;
            return this;
        }

        public Builder setQuery(String query) {
            this.query = query;
            return this;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder setPage(int page) {
            this.page = page;
            return this;
        }

        public THOptions build() {
            return new THOptions(this);
        }
    }
}
