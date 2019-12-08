package com.iuce.news;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class NewsAPI {
    static final String TAG = "NewsAPI"; // May be in `strings.xml`

    private static final String API_KEY = "cf9168e3e5ff4e8987492262f92632fb"; // Not secure!
    private static final String MAIN_URL = "https://newsapi.org/v2/top-headlines?country=tr&apiKey=" + API_KEY;

    /**
     * https://developer.android.com/training/volley/simple.html
     * @return
     */
    public static void requestNewsDatas(Context context, ResponseListener responseListener) {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MAIN_URL, (response) -> {
            // https://stackoverflow.com/a/9606629/9770490
            try {
                // new JSONObject(response).getJSONArray("articles").get(1).getString("source")
                JSONObject responseObject = new JSONObject(response);
                String status = responseObject.getString("status");
                if (status.equals("ok")) {
                    JSONArray articles = new JSONObject(response).getJSONArray("articles");

                    ArrayList<NewsData> newsDataList = new ArrayList<>();
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject article = articles.getJSONObject(i);
                        newsDataList.add(new NewsData(
                                article.getString("title"),
                                article.getString("description"),
                                article.getString("urlToImage")
                        ));
                        Log.i(TAG, "getNewsDatas: " + newsDataList.get(i));
                    }

                    responseListener.onResponse(newsDataList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        queue.add(stringRequest);
    }

    interface ResponseListener {
        void onResponse(ArrayList<NewsData> newsDataList);
    }

    public static class NewsData {
        String title;
        String description;
        String urlToImage;
        // TODO: News added

        NewsData(String title, String description, String urlToImage) {
            this.title = title;
            this.description = description;
            this.urlToImage = urlToImage;
        }
    }
}
