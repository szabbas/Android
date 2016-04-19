/*
 * Copyright (c) 2016 Newt Global India Pvt. Ltd.
 */

package com.app.newt.trackreal.utilities;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rajeev on 16/4/16.
 * Newt Global India Pvt Ltd.
 * krajeev@newtglobal.com
 */
public class OkHttpClientInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        long t1 = System.nanoTime();
        Log.i("REQUEST", request.url()
                + " " + chain.connection()
                + " " + request.headers());

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.i("RESPONSE", response.request().url()
                + " " + (t2 - t1) / 1e6d
                + " " + response.headers());

        return response;
    }

    public static void backgroundThreadShortToast(final Context context, final String msg)
    {
        if(context != null && msg != null)
        {
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {

                @Override
                public void run()
                {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
