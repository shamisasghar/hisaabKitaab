package com.technology.hisabKitab.Api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://onesignal.com/";
    private static Retrofit retrofit = null;

    public static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        clientBuilder.addInterceptor(logging);
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Cache-Control", "no-cache")
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body());
                String token = "Basic ZjY3M2ViMmYtOGRjYy00YTNkLTgwNTQtNjY3YmYzM2U2MDRj";
                if (token != null) {
                    requestBuilder.header("Authorization", token);
                }
                Request request = requestBuilder.build();
                return chain.proceed(request);

            }
        });

        return clientBuilder.build();
    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
