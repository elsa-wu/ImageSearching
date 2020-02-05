package com.example.elsa.imagesearching.mvp.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.elsa.imagesearching.base.BaseBean;
import com.example.elsa.imagesearching.base.BaseObserver;
import com.example.elsa.imagesearching.mvp.model.ImageBean;
import com.example.elsa.imagesearching.mvp.model.contract.SearchContract;
import com.example.elsa.imagesearching.net.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public  class SearchPresenter{
    private int nextCurrentPage = 2;
    private final SearchContract searchContract;
    private final Context context;

    //Binding contract class
    public SearchPresenter(SearchContract searchContract, Context context) {
        this.searchContract = searchContract;
        this.context = context;
    }

    // Get picture request
    public void querySearchData(String words) {
        RetrofitFactory.getInstance().API()
                .getImages(words)
                .compose(this.<BaseBean<ImageBean>>setThread())
                .subscribe(new BaseObserver<ImageBean>() {
                    @Override
                    protected void onSuccess(BaseBean<ImageBean> t) {
                        if (t.getData().size() > 0) {
                            searchContract.setSearchResult(t.getData());

                        } else {
                            searchContract.setEmptyView();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    //Load More Data
    public void loadMoreData(String words) {
        RetrofitFactory.getInstance().API()
                .getMoreImages("/3/gallery/search/" + nextCurrentPage + "?q=" + words)
                .compose(this.<BaseBean<ImageBean>>setThread())
                .subscribe(new BaseObserver<ImageBean>() {
                    @Override
                    protected void onSuccess(BaseBean<ImageBean> t) {
                        nextCurrentPage ++;
                        if (t.getData().size() > 0) {
                            searchContract.setSearchResult(t.getData());
                        } else {
                            searchContract.setEmptyView();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
