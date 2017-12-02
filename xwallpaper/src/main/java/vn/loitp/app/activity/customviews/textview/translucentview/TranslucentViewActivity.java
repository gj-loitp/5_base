package vn.loitp.app.activity.customviews.textview.translucentview;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.app.base.BaseActivity;
import com.loitp.xwallpaper.R;

//guide https://github.com/mallethugo/translucent-android

public class TranslucentViewActivity extends BaseActivity {
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
        return R.layout.activity_translucent_view;
    }

}
