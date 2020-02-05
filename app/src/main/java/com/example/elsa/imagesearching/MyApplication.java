package com.example.elsa.imagesearching;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private MyApplication instance;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    /**
     * singleton
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public MyApplication getInstance(){
        if(instance == null)
            return new MyApplication();
        return instance;
    }

    /**
     * Called when the application is created, this method can be overridden to instantiate the
     * application singleton and to create and instantiate any application state variable or
     * shared resource
     */
    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }



}
