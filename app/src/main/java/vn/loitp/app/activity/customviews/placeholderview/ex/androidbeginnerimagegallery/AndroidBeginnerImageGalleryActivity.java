package vn.loitp.app.activity.customviews.placeholderview.ex.androidbeginnerimagegallery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.utils.util.ToastUtils;

import loitp.basemaster.R;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;

public class AndroidBeginnerImageGalleryActivity extends BaseFontActivity {
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
                                        LLog.INSTANCE.d(TAG, "setLoadMoreListener");
                                    }
                                });
                            }
                        }
                    }
                };
        mGalleryView.addOnScrollListener(mOnScrollListener);
    }
}
