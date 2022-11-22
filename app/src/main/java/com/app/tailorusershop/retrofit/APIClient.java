package com.app.tailorusershop.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClient {
    private static Retrofit retrofit;
    private static Retrofit retrofitScalar;
    private static final String BASE_URL = "http://sigmas.in/tailor/";

    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


    public static Retrofit getAPIClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getScalarAPIClient() {
        if (retrofitScalar == null) {
            retrofitScalar = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build())
                    .build();
        }
        return retrofitScalar;
    }


}
