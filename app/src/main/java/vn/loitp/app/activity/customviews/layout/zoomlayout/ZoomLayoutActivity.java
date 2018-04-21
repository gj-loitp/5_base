package vn.loitp.app.activity.customviews.layout.zoomlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.utils.util.ToastUtils;

//read more
//https://github.com/natario1/ZoomLayout/?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6282

public class ZoomLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("Click button bt_1");
            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("Click button bt_2");
            }
        });

        /*zoomLayout.getEngine().panTo(x, y, true);
        zoomLayout.getEngine().panBy(deltaX, deltaY, true);
        zoomLayout.getEngine().zoomTo(zoom, true);
        zoomLayout.getEngine().zoomBy(factor, true);
        zoomLayout.getEngine().realZoomTo(realZoom, true);
        zoomLayout.getEngine().moveTo(zoom, x, y, true);*/
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
        return R.layout.activity_zoom_layout;
    }
}
