package com.example.elsa.imagesearching.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.elsa.imagesearching.R;
import com.example.elsa.imagesearching.mvp.model.ImageBean;


import java.util.List;

import androidx.annotation.Nullable;

public class SearchAdapter extends BaseQuickAdapter<ImageBean, BaseViewHolder> {
    private final Context context;

    public SearchAdapter(int layoutResId, @Nullable List<ImageBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageBean item) {
        if (item.getImages() != null) {
            Log.i("imageLink", item.getImages().get(0).getLink());
            Glide.with(context).load(item.getImages().get(0).getLink()).error(R.mipmap.ic_launcher_round).into((ImageView) helper.getView(R.id.img_search_image));
        }
    }
}
