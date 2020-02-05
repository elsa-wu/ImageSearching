package com.example.elsa.imagesearching.ui.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.elsa.imagesearching.R;
import com.example.elsa.imagesearching.base.BaseActivity;
import com.example.elsa.imagesearching.mvp.model.ImageBean;
import com.example.elsa.imagesearching.mvp.model.contract.SearchContract;
import com.example.elsa.imagesearching.mvp.presenter.SearchPresenter;
import com.example.elsa.imagesearching.ui.adapter.SearchAdapter;
import com.example.elsa.imagesearching.utils.ViewAnimUtils;
import com.example.elsa.imagesearching.view.ClearEditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class SearchActivity extends BaseActivity implements SearchContract {
    @BindView(R.id.layout_container)
    LinearLayout containerLinearLayout;
    @BindView(R.id.layout_frame)
    RelativeLayout frameRelativeLayout;
    @BindView(R.id.fab_circle)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.tv_cancel)
    TextView canCelTextView;
    @BindView(R.id.et_search_view)
    ClearEditText searchEditText;
    @BindView(R.id.mRecyclerView_search)
    RecyclerView searchRecyclerView;



    /**
     * Whether to load more
     */
    private boolean loadingMore = false;

    private SearchPresenter searchPresenter;

    private String keyWords = null;

    @Override
    public int layoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        searchPresenter = new SearchPresenter(this, this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation(); // Enter animation
            setUpExitAnimation(); // Exit animation
        } else {
            setUpView();
        }

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        // cancel and back
        canCelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchEditText.requestFocus();
        //search button
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    closeSoftKeyboard();
                    keyWords = searchEditText.getText().toString();
                    if (TextUtils.isEmpty(keyWords)) {
                        Toast.makeText(SearchActivity.this, "Please enter keywords", Toast.LENGTH_SHORT).show();
                    } else {
                        searchPresenter.querySearchData(keyWords);
                    }
                }
                return false;
            }
        });


        //Automatic loading
        searchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int itemCount = Objects.requireNonNull(searchRecyclerView.getLayoutManager()).getItemCount();  //get all item count
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                assert layoutManager != null;
                int lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition(); // find Last Visible Item Position
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true;
                    searchPresenter.loadMoreData(keyWords);
                }

            }
        });


    }

    @Override
    public void setSearchResult(final List<ImageBean> result) {
        Log.i("result", result.toString());
        SearchAdapter searchAdapter = new SearchAdapter(R.layout.item_search_image, result, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setAdapter(searchAdapter);

        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SearchActivity.this, ImageDetailActivity.class);
                intent.putExtra("detail", result.get(position));
                startActivity(intent);
            }
        });
    }

    /**
     * Close the softKeyboard
     */
    private void closeSoftKeyboard() {
        closeKeyBord(searchEditText, getApplicationContext());
    }

    /**
     * EmptyView
     */
    @Override
    public void setEmptyView() {
        Toast.makeText(this, "Sorry, no pictures found", Toast.LENGTH_LONG).show();
    }

    /**
     * Enter Animation
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);

        this.getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();   //Two controls exchange
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    /**
     * ExitAnimation
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpExitAnimation() {
        Fade fade = new Fade();
        this.getWindow().setReturnTransition(fade);
        fade.setDuration(300L);
    }

    private void setUpView() {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(300);
        containerLinearLayout.startAnimation(animation);
        containerLinearLayout.setVisibility(View.VISIBLE);
        //openKeyBord
        openKeyBord(searchEditText, getApplicationContext());
    }

    /**
     * Show Reveal Animate
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        ViewAnimUtils.animateRevealShow(
                this, frameRelativeLayout,
                floatingActionButton.getWidth() / 2, R.color.backgroundColor,
                new ViewAnimUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        setUpView();
                    }
                });
    }


    // Return event
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimUtils.animateRevealHide(
                    this, frameRelativeLayout,
                    floatingActionButton.getWidth() / 2, R.color.backgroundColor,
                    new ViewAnimUtils.OnRevealAnimationListener() {
                        @Override
                        public void onRevealHide() {
                            defaultBackPressed();
                            containerLinearLayout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onRevealShow() {

                        }
                    });
        } else {
            defaultBackPressed();
        }
    }

    // Default back
    private void defaultBackPressed() {
        closeSoftKeyboard();
        super.onBackPressed();
    }
}
