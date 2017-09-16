package vn.loitp.app.activity.placeholderview.androidbeginnerimagegallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.mindorks.placeholderview.PlaceHolderView;

import vn.loitp.app.base.BaseActivity;
import vn.loitp.livestar.R;

public class AndroidBeginnerImageGalleryActivity extends BaseActivity {
    private PlaceHolderView mGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGalleryView = (PlaceHolderView) findViewById(R.id.galleryView);
        mGalleryView.getBuilder().setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        for (int i = 0; i < 50; i++) {
            mGalleryView.addView(new GalleryItem(getResources().getDrawable(R.drawable.iv)));
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
