package com.iuce.news.api;

import android.content.Context;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class  WeatherAPI {

    private static final String MAIN_URL = "https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22" ;

    static void requestWeatherData(Context context, ResponseListener responseListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MAIN_URL, (response) -> {
            try {
                JSONObject responseObject = new JSONObject(response);
                String cod = responseObject.getString("code");
                if (cod.equals("200")) { // If success
                    // TODO: Weather data parsing
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, Throwable::printStackTrace);

    }

    interface ResponseListener {
        void onResponse(ArrayList<Data> weatherDataList);
    }

    static class Data {

    }
}
