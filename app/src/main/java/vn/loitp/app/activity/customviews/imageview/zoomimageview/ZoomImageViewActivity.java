package vn.loitp.app.activity.customviews.imageview.zoomimageview;

import android.app.Activity;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.imageview.zoomimageview.ColorGridDrawable;
import vn.loitp.views.layout.zoomlayout.lib.ZoomImageView;

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
    protected int setLayoutResourceId() {
        return R.layout.activity_zoomimageview;
    }
}
