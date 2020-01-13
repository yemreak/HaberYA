package com.iuce.news.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iuce.news.db.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsAPI {
    public static final String TAG = "NewsAPI"; // May be in `strings.xml`

    private static final String[] API_KEYS = {"cf9168e3e5ff4e8987492262f92632fb", "f82c72913e944b0c838e24a52e90db8c"}; // Not secure!
    private static final String URL_TEMPLATE = "https://newsapi.org/v2/%s?%s&apiKey=%s";

    public static String category = null;
    public static String country = null;


    private static String getRandomAPI() {
        return API_KEYS[new Random().nextInt(API_KEYS.length)];
    }

    /**
     * @see <a href="https://android.yemreak.com/temel/http-istekleri">HTTPS istekleri ~ YEmreAk</a>
     * @see <a href="https://developer.android.com/training/volley/simple.html">Volley ~ Android
     * Developer</a>
     */
    public static void requestNewsData(Context context, String url, ResponseListener responseListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, (response) -> {
            try {
                convertStringToNewsList(response, responseListener);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        queue.add(stringRequest);
    }

    public static void requestTopHeadlines(Context context, ResponseListener responseListener, @Nullable NewsAPIOptions options) {
        String url = BuildURL("top-headlines", options);
        requestNewsData(context, url, responseListener);
    }

    public static void requestSources(Context context, ResponseListener responseListener, @Nullable NewsAPIOptions options) {
        String url = BuildURL("sources", options);
        requestNewsData(context, url, responseListener);
    }

    public static void requestEverything(Context context, ResponseListener responseListener, @Nullable NewsAPIOptions options) {
        String url = BuildURL("everything", options);
        requestNewsData(context, url, responseListener);
    }

    private static String BuildURL(String head, @Nullable NewsAPIOptions options) {
        category = head;

        if (options == null) {
            options = NewsAPIOptions.Builder().build();
        }

        String url = String.format(
                URL_TEMPLATE, head,
                options.BuildURL(),
                getRandomAPI()
        );

        category = options.getCategory();
        country = options.getCountry();

        Log.i(TAG, "BuildURL: NewsAPI için url oluşturuldu:" + url);

        return url;
    }

    /**
     * Yanıt metnini haber listesine çevirir
     *
     * @param response         HTTP isteğinden gelen yanıt
     * @param responseListener Yanıt haber listesine çevirildiği zaman çalışır
     * @throws JSONException JSON ayrıştırması sırasında çıkan hata
     * @see <a href="https://android.yemreak.com/veriler/json-yoenetimi">JSON Yönetimi ~ YEmreAk</a>
     */
    private static void convertStringToNewsList(String response, ResponseListener responseListener) throws JSONException {
        JSONObject responseObject = new JSONObject(response);
        String status = responseObject.getString("status");
        if (status.equals("ok")) {
            JSONArray articles = new JSONObject(response).getJSONArray("articles");

            List<News> newsList = new ArrayList<>();
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                newsList.add(convertArticleToNews(article));
            }

            responseListener.onResponse(newsList);
        } else {
            responseListener.onResponse(null);
        }
    }

    private static News convertArticleToNews(JSONObject article) throws JSONException {
        News news =  new News(
                article.getString("title"),
                article.getString("description"),
                article.getString("urlToImage"),
                article.getString("url"),
                article.getString("content"),
                article.getJSONObject("source").getString("name"),
                article.getString("publishedAt"),
                category,
                country
        );

        Log.v(TAG, "convertArticleToNews: " + news);

        return news;
    }

    public interface ResponseListener {
        void onResponse(List<News> newsDataList);
    }
}
