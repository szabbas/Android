/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.trackreal.utilities;

import android.util.Log;

import com.app.newt.trackreal.ApplicationController;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Rajeev on 16/4/16.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public class NetworkConnectivityUrlClient extends OkHttpClient {

    private OkHttpClient okHttpClient;

    /**
     *
     * @param okHttpClient
     */
    public NetworkConnectivityUrlClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public Call newCall(Request request) {
        if (!ApplicationController.getInstance().isNetworkAvailable()) {
            Log.d("NO INTERNET", request.toString());
        }
        return okHttpClient.newCall(request);
    }
}
