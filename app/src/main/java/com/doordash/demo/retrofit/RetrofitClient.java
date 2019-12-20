package com.doordash.demo.retrofit;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_RESTAURANT_URL = "https://api.doordash.com/v2/";

    private static Retrofit retrofitInstance ;

    @NonNull
    public static Retrofit getInstance() {

        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_RESTAURANT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return retrofitInstance;
    }

    private RetrofitClient() {
    }
}
