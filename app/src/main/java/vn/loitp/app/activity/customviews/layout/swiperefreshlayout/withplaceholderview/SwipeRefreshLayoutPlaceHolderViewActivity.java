package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withplaceholderview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery.Image;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery.ImageTypeBig;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery.ImageTypeSmallList;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery.Utils;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;

public class SwipeRefreshLayoutPlaceHolderViewActivity extends BaseActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private PlaceHolderView mGalleryView;
    private PlaceHolderView.OnScrollListener mOnScrollListener;
    private LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageList = Utils.loadImages(activity);

        llMain = (LinearLayout) findViewById(R.id.ll_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        //mGalleryView = (PlaceHolderView) findViewById(R.id.galleryView);
        createNewPlaceHolderView();
        //LUIUtil.setPullLikeIOSVertical(mGalleryView);

        setupGallery();
    }

    private void createNewPlaceHolderView() {
        if (mGalleryView != null) {
            return;
        }
        mGalleryView = new PlaceHolderView(activity);
        llMain.addView(mGalleryView);
        setLoadMoreListener();
    }

    private void removePlaceHolderView() {
        if (mGalleryView == null) {
            return;
        }
        llMain.removeView(mGalleryView);
        mGalleryView = null;
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_swipe_refresh_placeholder_view_layout;
    }

    private List<Image> imageList = new ArrayList<>();

    private void setupGallery() {
        mGalleryView.addView(new ImageTypeSmallList(activity, imageList));
        for (int i = 0; i < imageList.size(); i++) {
            mGalleryView.addView(new ImageTypeBig(activity, mGalleryView, imageList.get(i).getImageUrl()));
        }
        mGalleryView.addView(new ImageTypeSmallList(activity, imageList));
    }

    private void refresh() {
        mGalleryView.removeAllViews();
        removePlaceHolderView();
        LUIUtil.setDelay(2000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                createNewPlaceHolderView();
                setupGallery();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadMore() {
        LLog.d(TAG, ">>>>loadMore");
        LUIUtil.setDelay(2000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                setupGallery();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setLoadMoreListener() {
        mOnScrollListener =
                new PlaceHolderView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        PlaceHolderView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                            int totalItemCount = linearLayoutManager.getItemCount();
                            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (totalItemCount > 0 && totalItemCount == lastVisibleItem + 1) {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (swipeRefreshLayout.isRefreshing()) {
                                            return;
                                        }
                                        swipeRefreshLayout.setRefreshing(true);
                                        loadMore();
                                    }
                                });
                            }
                        }
                    }
                };
        mGalleryView.addOnScrollListener(mOnScrollListener);
    }
}
