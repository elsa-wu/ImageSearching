package com.example.elsa.imagesearching.ui.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.elsa.imagesearching.R;
import com.example.elsa.imagesearching.base.BaseActivity;
import com.example.elsa.imagesearching.database.DBDao;
import com.example.elsa.imagesearching.mvp.model.CommentBean;
import com.example.elsa.imagesearching.mvp.model.ImageBean;
import com.example.elsa.imagesearching.ui.adapter.CommentAdapter;
import com.example.elsa.imagesearching.view.ClearEditText;
import com.stx.xhb.androidx.XBanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ImageDetailActivity extends BaseActivity {

    @BindView(R.id.xbanner)
    XBanner xBanner;
    @BindView(R.id.tv_img_title)
    TextView titleTextView;
    @BindView(R.id.edit_comment)
    ClearEditText commentEditText;
    @BindView(R.id.img_back)
    ImageView backImageView;
    @BindView(R.id.btn_submit_comment)
    Button commentSubmitButton;
    @BindView(R.id.mRecyclerView_comment)
    RecyclerView commentRecyclerView;

    private List<CommentBean> commentBeanList = new ArrayList<>();
    private CommentAdapter commentAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });    //back

        final ImageBean imageDetail = (ImageBean) getIntent().getSerializableExtra("detail");
        titleTextView.setText(Objects.requireNonNull(imageDetail).getTitle());

        //banner add images
        xBanner.setBannerData(imageDetail.getImages());
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                if (imageDetail.getImages() != null) {
                    Glide.with(ImageDetailActivity.this).load(imageDetail.getImages().get(position).getLink())
                            .error(R.mipmap.ic_launcher_round).into((ImageView) view);
                }
            }
        });

        //Click Submit Comment
        commentSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(commentEditText.getText().toString())) {
                    Toast.makeText(ImageDetailActivity.this, "No comments have been entered", Toast.LENGTH_SHORT).show();
                } else {
                    CommentBean commentBean = new CommentBean(imageDetail.getId(), commentEditText.getText().toString());
                    Log.i("comments", commentEditText.getText().toString());
                    Log.i("commentsBean", commentBean.toString());
                    DBDao.getInstance(ImageDetailActivity.this).insert(commentBean);      // Click submit to save to database

                    //Refresh  RecyclerView data
                    commentBeanList.add(0,commentBean);
                    commentAdapter.notifyDataSetChanged();
                    commentEditText.setText("");
                }
            }
        });

        //Initial RecyclerView
        if (DBDao.getInstance(ImageDetailActivity.this).query(imageDetail.getId()).size() != 0) {
            commentBeanList = DBDao.getInstance(ImageDetailActivity.this).query(imageDetail.getId());
            Collections.reverse(commentBeanList); // Reverse the list and put the post comment at the top

        }

        commentAdapter = new CommentAdapter(R.layout.item_comment, commentBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ImageDetailActivity.this);
        commentRecyclerView.setLayoutManager(linearLayoutManager);
        commentRecyclerView.setAdapter(commentAdapter);


    }

}
