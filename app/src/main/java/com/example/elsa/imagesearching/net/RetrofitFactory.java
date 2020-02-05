package com.example.elsa.imagesearching.net;

import com.example.elsa.imagesearching.MyApplication;
import com.example.elsa.imagesearching.api.APIFunction;
import com.example.elsa.imagesearching.base.IBaseConstant;
import com.example.elsa.imagesearching.utils.InterceptorUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * rxjava + retrofit + okhttp
 */

public class RetrofitFactory {
    private static RetrofitFactory sRetrofitFactory;
    private static APIFunction sAPIFunction;

    private RetrofitFactory() {
        File cacheFile = new File(MyApplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb cache


        //set Okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(IBaseConstant.httpTime, TimeUnit.SECONDS) //connectTimeout time
                .readTimeout(IBaseConstant.httpTime, TimeUnit.SECONDS)
                .writeTimeout(IBaseConstant.httpTime, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.HeaderInterceptor()) // add head
                .addInterceptor(InterceptorUtil.LogInterceptor())   //add  LogInterceptor
                .build();
        //set retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IBaseConstant.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) //gson
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //rxjava
                .build();

        sAPIFunction = retrofit.create(APIFunction.class);
    }
    // Get an instance of retrofit
    public static RetrofitFactory getInstance() {
        if (sRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (sRetrofitFactory == null) {
                    sRetrofitFactory = new RetrofitFactory();
                }
            }
        }
        return sRetrofitFactory;
    }


    public APIFunction API() {
        return sAPIFunction;
    }
}
