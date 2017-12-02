package vn.loitp.app.activity.customviews.imageview.zoomimageview;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.app.activity.customviews.layout.zoomlayout.lib.ZoomImageView;
import vn.loitp.app.base.BaseActivity;
import com.loitp.xwallpaper.R;

public class ZoomImageViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ZoomImageView zoomImageView = (ZoomImageView) findViewById(R.id.zoom_iv);
        zoomImageView.setImageDrawable(new ColorGridDrawable());

        /*zoomImageView.getEngine().panTo(x, y, true);
        zoomImageView.getEngine().panBy(deltaX, deltaY, true);
        zoomImageView.getEngine().zoomTo(zoom, true);
        zoomImageView.getEngine().zoomBy(factor, true);
        zoomImageView.getEngine().realZoomTo(realZoom, true);
        zoomImageView.getEngine().moveTo(zoom, x, y, true);*/
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
        return R.layout.activity_zoomimageview;
    }
}
