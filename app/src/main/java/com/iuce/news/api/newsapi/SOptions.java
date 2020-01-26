package com.iuce.news.api.newsapi;


/**
 * Sources işlemleri için gerekli ayarlar
 *
 * @see <a href="https://newsapi.org/docs/endpoints/sources">Sources ~ NewsAPI</a>
 */
public class SOptions extends Options {

    public static final String HEAD = "sources";

    private Category category;

    private Language language;

    private Country country;

    SOptions(Builder builder) {
        this.country = builder.country;
        this.category = builder.category;
        this.language = builder.language;

    }

    public static Builder Builder() {
        return new Builder();
    }

    public String buildUrl(String apiKey) {
        return super.buildUrl(HEAD, buildQueries(), apiKey);
    }

    private String buildQueries() {
        return trimQuery("" // 🦅
                + generateQuery("category", category)
                + generateQuery("language", language)
                + generateQuery("country", country)
        );
    }

    public static final class Builder {

        private Category category;

        private Language language;

        private Country country;

        public Builder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder setLanguage(Language language) {
            this.language = language;
            return this;
        }

        public Builder setCountry(Country country) {
            this.country = country;
            return this;
        }

        public SOptions build() {
            return new SOptions(this);
        }
    }
}
