package com.example.riltempoapp;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String LOG_TAG = ApiClient.class.getSimpleName();

    public static String EDFAPI_BASE_URL = "https://particulier.edf.fr";

    private static Retrofit retrofit = null;

    static Retrofit get() {

        // make a logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        // make a Http Header interceptor
        Interceptor headerInterceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };

        // create the Http Client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor) // always add logging interceptor at the end
                .build();
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(EDFAPI_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return retrofit;
    }

}
