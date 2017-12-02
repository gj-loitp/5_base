package vn.loitp.app.activity.customviews.textview.zoomtextview;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.app.activity.customviews.textview.scrollnumber.lib.MultiScrollNumber;
import vn.loitp.app.base.BaseActivity;
import com.loitp.xwallpaper.R;

public class ZoomTextViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return R.layout.activity_zoom_textview;
    }

}
