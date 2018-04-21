package vn.loitp.app.activity.customviews.placeholderview.ex.androidbeginnerimagegallery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed.Feed;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed.HeadingView;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed.Info;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed.InfoView;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed.Utils;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.placeholderview.lib.placeholderview.ExpandablePlaceHolderView;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;

public class AndroidBeginnerImageGalleryActivity extends BaseActivity {
    private PlaceHolderView mGalleryView;
    private PlaceHolderView.OnScrollListener mOnScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGalleryView = (PlaceHolderView) findViewById(R.id.galleryView);

        LUIUtil.setPullLikeIOSVertical(mGalleryView);

        mGalleryView.getBuilder().setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        for (int i = 0; i < 100; i++) {
            mGalleryView.addView(new GalleryItem(ContextCompat.getDrawable(activity, R.drawable.logo), i, new GalleryItem.Callback() {
                @Override
                public void onClick(int position) {
                    ToastUtils.showShort("Click " + position);
                }
            }));
        }
        setLoadMoreListener();
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
        return R.layout.activity_android_beginner_image_gallery;
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
                                        LLog.d(TAG, "setLoadMoreListener");
                                    }
                                });
                            }
                        }
                    }
                };
        mGalleryView.addOnScrollListener(mOnScrollListener);
    }
}
