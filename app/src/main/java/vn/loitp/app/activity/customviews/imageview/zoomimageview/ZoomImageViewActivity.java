package vn.loitp.app.activity.customviews.imageview.zoomimageview;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.views.imageview.zoomimageview.ColorGridDrawable;
import com.views.layout.zoomlayout.ZoomImageView;

import loitp.basemaster.R;

public class ZoomImageViewActivity extends BaseFontActivity {

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
