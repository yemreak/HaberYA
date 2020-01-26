package com.iuce.news.api.newsapi;

import java.util.Date;

/**
 * Everything işlemleri için gerekli ayarlar
 *
 * @see <a href="https://newsapi.org/docs/endpoints/everything">Everything ~ NewsAPI</a>
 */
public class EOptions extends Options {

    public static final String HEAD = "everything";

    private String query;
    private String qInTitle;
    private String sources;
    private String domains;
    private String excludeDomains;

    private Date from;
    private Date to;

    private Language language;
    private SortBy sortBy;

    private int pageSize;
    private int page;

    EOptions(Builder builder) {
        this.query = builder.query;
        this.qInTitle = builder.qInTitle;
        this.sources = builder.sources;
        this.domains = builder.domains;
        this.excludeDomains = builder.excludeDomains;
        this.from = builder.from;
        this.to = builder.to;
        this.language = builder.language;
        this.sortBy = builder.sortBy;
        this.pageSize = builder.pageSize;
        this.page = builder.page;
    }

    public static THOptions.Builder Builder() {
        return new THOptions.Builder();
    }

    public String buildUrl(String apiKey) {
        return super.buildUrl(HEAD, buildQueries(), apiKey);
    }

    private String buildQueries() {
        return trimQuery("" // 🦅
                + generateQuery("q", query)
                + generateQuery("qInTitle", qInTitle)
                + generateQuery("sources", sources)
                + generateQuery("domains", domains)
                + generateQuery("excludeDomains", excludeDomains)
                + generateQuery("from", from)
                + generateQuery("to", to)
                + generateQuery("language", language)
                + generateQuery("sortBy", sortBy)
                + generateQuery("pageSize", pageSize)
                + generateQuery("page", page)
        );
    }

    public static final class Builder {

        private String query;
        private String qInTitle;
        private String sources;
        private String domains;
        private String excludeDomains;

        private Date from;
        private Date to;

        private Language language;
        private SortBy sortBy;

        private int pageSize;
        private int page;

        public Builder setQuery(String query) {
            this.query = query;
            return this;
        }

        public Builder setQInTitle(String qInTitle) {
            this.qInTitle = qInTitle;
            return this;
        }

        public Builder setSources(String sources) {
            this.sources = sources;
            return this;
        }

        public Builder setDomains(String domains) {
            this.domains = domains;
            return this;
        }

        public Builder setExcludeDomains(String excludeDomains) {
            this.excludeDomains = excludeDomains;
            return this;
        }

        public Builder setFrom(Date from) {
            this.from = from;
            return this;
        }

        public Builder setTo(Date to) {
            this.to = to;
            return this;
        }

        public Builder setLanguage(Language language) {
            this.language = language;
            return this;
        }

        public Builder setSortBy(SortBy sortBy) {
            this.sortBy = sortBy;
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

        public EOptions build() {
            return new EOptions(this);
        }
    }
}
