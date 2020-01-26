package com.iuce.news.api.newsapi;

public class THOptions extends BaseOptions {

    public static final String HEAD = "top-headlines";

    private String country;

    THOptions(Builder builder) {
        super(builder);
        this.country = builder.country;
    }

    public static Builder Builder() {
        return new Builder();
    }

    @Override
    public String buildUrl(String apiKey) {
        return super.buildUrl(HEAD, apiKey);
    }

    @Override
    String buildQueries() {
        return "" // 🦅
                + generateQuery("country", country)
                + super.buildQueries();
    }

    public String getCountry() {
        return country;
    }

    public enum Country {

        AE, AR, AT, AU, BE, BG, BR, CA, CH, CN, CO, CU, CZ, DE, EG, FR,
        GB, GR, HK, HU, ID, IE, IL, IN, IT, JP, KR, LT, LV, MA, MX, MY,
        NG, NL, NO, NZ, PH, PL, PT, RO, RS, RU, SA, SE, SG, SI, SK, TH,
        TR, TW, UA, US, VE, ZA;

        public String getValue() {
            return this.name().toLowerCase();
        }
    }

    public static final class Builder extends BaseOptions.Builder {

        private String country;

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

        public Builder setCountry(Country country) {
            this.country = country.getValue();
            return this;
        }


        @Override
        public THOptions build() {
            return new THOptions(this);
        }
    }
}
