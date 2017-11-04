package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview.lib;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.app.base.BaseActivity;
import vn.loitp.livestar.R;

public class ParallaxRecyclerViewActivity extends BaseActivity {

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
        return R.layout.activity_parallax_recycler_view;
    }
}
