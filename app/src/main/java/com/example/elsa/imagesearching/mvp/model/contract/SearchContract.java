package com.example.elsa.imagesearching.mvp.model.contract;

import com.example.elsa.imagesearching.mvp.model.ImageBean;

import java.util.List;

public interface SearchContract {

    /**
     * Set results returned by search keywords
     */
    void setSearchResult(List<ImageBean> result);

    /**
     * Set empty view
     */
    void setEmptyView();

}
