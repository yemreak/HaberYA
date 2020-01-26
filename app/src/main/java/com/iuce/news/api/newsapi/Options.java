package com.iuce.news.api.newsapi;


import androidx.annotation.NonNull;

import java.util.Date;

/**
 * NewsAPI kullanımı için gerekli olan temel ayarlar
 *
 * @see <a href="https://newsapi.org/docs/endpoints">Endpoints ~ NewsAPI</a>
 */
public abstract class Options {

    private static final String URL_TEMPLATE = "https://newsapi.org/v2/%s?%s&apiKey=%s";

    String buildUrl(String head, String queries, String apiKey) {
        return String.format(URL_TEMPLATE, head, queries, apiKey);
    }

    String generateQuery(@NonNull String name, String value) {
        if (value == null)
            return "";
        return name + "=" + value + "&";
    }

    String generateQuery(@NonNull String name, int value) {
        if (value == -1)
            return "";
        return name + "=" + value + "&";
    }

    @SuppressWarnings("SameParameterValue")
    String generateQuery(@NonNull String name, Language value) {
        if (value == null)
            return "";
        return name + "=" + value.toString() + "&";
    }

    @SuppressWarnings("SameParameterValue")
    String generateQuery(@NonNull String name, Date value) {
        if (value == null)
            return "";
        return name + "=" + value.toString() + "&";
    }

    @SuppressWarnings("SameParameterValue")
    String generateQuery(@NonNull String name, SortBy value) {
        if (value == null)
            return "";
        return name + "=" + value.toString() + "&";
    }

    @SuppressWarnings("SameParameterValue")
    String generateQuery(@NonNull String name, Category value) {
        if (value == null)
            return "";
        return name + "=" + value.toString() + "&";
    }

    @SuppressWarnings("SameParameterValue")
    String generateQuery(@NonNull String name, Country value) {
        if (value == null)
            return "";
        return name + "=" + value.toString() + "&";
    }

    String trimQuery(@NonNull String string) {
        int lastCharIndex = string.length() - 1;
        if (string.length() > 0 && string.charAt(lastCharIndex) == '&')
            return string.substring(0, lastCharIndex);
        return string;
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
}
