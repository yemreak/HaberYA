package com.iuce.news.api;

/**
 * @see <a href="https://newsapi.org/docs/endpoints/top-headlines">NewsAPI Doc</a>
 */
public class NewsAPIOptions {
    /**
     * The 2-letter ISO 3166-1 code of the country you want to get headlines for
     */
    private String country;
    private String category;
    private String sources;
    private String query;

    private int pageSize;
    private int page;

    private NewsAPIOptions(Builder builder) {
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

    String BuildURL() {
        StringBuilder stringBuilder = new StringBuilder();
        if (country != null) {
            stringBuilder.append("country=").append(country).append("&");
        }
        if (category != null) {
            stringBuilder.append("category=").append(category).append("&");
        }
        if (sources != null) {
            stringBuilder.append("sources=").append(sources).append("&");
        }
        if (query != null) {
            stringBuilder.append("q=").append(query).append("&");
        }
        if (pageSize != -1) {
            stringBuilder.append("pageSize=").append(pageSize).append("&");
        }
        if (page != -1) {
            stringBuilder.append("page=").append(page).append("&");
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
        }

        return stringBuilder.toString();
    }

    public String getCategory() {
        if (category == null) {
            return Category.ANY.getValue();
        }
        return category;
    }


    public String getCountry() {
        return country;
    }

    public enum Category {

        ANY, BUSINESS, ENTERTAINMENT, GENERAL, HEALTH, SCIENCE, SPORTS, TECHNOLOGY;

        public String getValue() {
            return this.name().toLowerCase();
        }
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

    /**
     * @see <a href="https://stackoverflow.com/a/1988035/9770490">Named Parameter idiom in Java</a>
     */
    public static final class Builder {

        String country = Country.TR.getValue();
        String category;
        String sources;
        String query;

        int pageSize = -1;
        int page = -1;

        public Builder setCountry(Country country) {
            this.country = country.getValue();
            return this;
        }

        public Builder setCategory(Category category) {
            this.category = category.getValue();
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

        public NewsAPIOptions build() {
            return new NewsAPIOptions(this);
        }
    }
}
