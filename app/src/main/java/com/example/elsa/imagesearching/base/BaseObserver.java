package com.example.elsa.imagesearching.base;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseBean<T>> {


    protected BaseObserver() {
    }


    //rxjava Subscribe
    @Override
    public void onSubscribe(Disposable d) {
    }

    /**
     * Get data header
     * However, the third party only has success, and the API is incomplete, which cannot be further improved
     */
    @Override
    public void onNext(BaseBean<T> tBaseEntity) {

        if (tBaseEntity.isSuccess()) {
            try {
                onSuccess(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // request success
    protected abstract void onSuccess(BaseBean<T> t);
    //request fail
    protected abstract void onFailure(Throwable e);
    //network error
    @Override
    public void onError(Throwable e) {

        try {
            if (e instanceof ConnectException || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e);
            } else {
                onFailure(e);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public void onComplete() {

    }

}
