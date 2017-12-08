package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withplaceholderview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageList = Utils.loadImages(activity);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        mGalleryView = (PlaceHolderView) findViewById(R.id.galleryView);
        //LUIUtil.setPullLikeIOSVertical(mGalleryView);

        setupGallery();
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
    protected Activity setActivity() {
        return this;
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
        LUIUtil.setDelay(2000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                setupGallery();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadMore() {
        LLog.d(TAG, "loadMore");
        LUIUtil.setDelay(2000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                List<Image> imageList = Utils.loadImages(activity);
                mGalleryView.addView(new ImageTypeSmallList(activity, imageList));
            }
        });
    }
}
