package com.example.elsa.imagesearching.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * InterceptorUtil
 *
 * Set log interception and header interception
 */

public class InterceptorUtil {
    private static final String TAG = "-------";

    //Logging Interceptor
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    //Set request header according to IMGUR's API
    public static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                builder.addHeader("Authorization","Client-ID 137cda6b5008a7c");
                request = builder.build();
                return chain.proceed(request);
            }
        };
    }
}
