package com.example.elsa.imagesearching.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.elsa.imagesearching.R;
import com.example.elsa.imagesearching.mvp.model.CommentBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CommentAdapter extends BaseQuickAdapter<CommentBean, BaseViewHolder> {
    public CommentAdapter(int layoutResId, @Nullable List<CommentBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, CommentBean item) {
        helper.setText(R.id.tv_comment, item.getComment());
    }
}
