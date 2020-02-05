package com.example.elsa.imagesearching.ui.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.elsa.imagesearching.R;
import com.example.elsa.imagesearching.base.BaseActivity;


public class HomeActivity extends BaseActivity {
    @BindView(R.id.layout_search)
    LinearLayout searchLinearLayout;
    @BindView(R.id.image_search_btn)
    ImageView searchImageView;

    @Override
    public int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        searchLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchActivity();
            }
        });
    }

    //Search icon animation
    private void openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, searchImageView, searchImageView.getTransitionName());
            ActivityCompat.startActivity(this, new Intent(this, SearchActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, SearchActivity.class));
        }
    }

    private long firstTime = 0;
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(HomeActivity.this, "One more exit ", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else{
            finish();
        }
    }
}
