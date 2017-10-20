package vn.loitp.app.activity.customviews.placeholderview.ex.androidbeginnerimagegallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;

import loitp.utils.util.ToastUtils;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.PlaceHolderView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class AndroidBeginnerImageGalleryActivity extends BaseActivity {
    private PlaceHolderView mGalleryView;

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
        return R.layout.activity_android_beginner_image_gallery;
    }
}
