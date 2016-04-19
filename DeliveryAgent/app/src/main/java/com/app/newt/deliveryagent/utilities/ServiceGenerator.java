/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.deliveryagent.utilities;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rajeev on 12/4/16.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public class ServiceGenerator {
    // base url of app.
    public static final String API_BASE_URL = "http://192.168.1.40:8080/services/";

    // OkHttpClient used for multiple http request, connection time-out.
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(new OkHttpClientInterceptor())
            .retryOnConnectionFailure(true)
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    /**
     * call server for a connection.
     *
     * @param serviceClass passing api class.
     * @param <S>          passing class.
     * @return api class.
     */
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder
                .client(okHttpClient)
                .build();
        return retrofit.create(serviceClass);
    }
}
