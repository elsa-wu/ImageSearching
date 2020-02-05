package com.example.elsa.imagesearching.api;

import com.example.elsa.imagesearching.base.BaseBean;
import com.example.elsa.imagesearching.base.IBaseConstant;
import com.example.elsa.imagesearching.mvp.model.ImageBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIFunction {

    //Get Images
    @GET(IBaseConstant.GetImageSearching)
    Observable<BaseBean<ImageBean>> getImages(@Query("q") String keyWord);

    //Get More Images
    @GET
    Observable<BaseBean<ImageBean>> getMoreImages(@Url String url);

}
