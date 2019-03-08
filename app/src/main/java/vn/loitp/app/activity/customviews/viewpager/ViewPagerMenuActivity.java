package vn.loitp.app.activity.customviews.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.AutoViewPagerActivity;
import vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout.ex.DetectViewPagerSwipeOutActivity;
import vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout2.ViewPagerSwipeOut2Activity;
import vn.loitp.app.activity.customviews.viewpager.lockableviewpager.LockableViewPagerActivity;
import vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager.ParallaxViewPagerActivity;
import vn.loitp.app.activity.customviews.viewpager.viewpagerwithtablayout.ViewPagerWithTabLayoutActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class ViewPagerMenuActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_auto_viewpager).setOnClickListener(this);
        findViewById(R.id.bt_parallax_viewpager).setOnClickListener(this);
        findViewById(R.id.bt_detect_viewpager_swipe_out).setOnClickListener(this);
        findViewById(R.id.bt_view_pager_tablayout).setOnClickListener(this);
        findViewById(R.id.bt_detect_viewpager_swipe_out_2).setOnClickListener(this);
        findViewById(R.id.bt_lockable_viewpager).setOnClickListener(this);
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
        return R.layout.activity_menu_view_pager;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_auto_viewpager:
                intent = new Intent(activity, AutoViewPagerActivity.class);
                break;
            case R.id.bt_parallax_viewpager:
                intent = new Intent(activity, ParallaxViewPagerActivity.class);
                break;
            case R.id.bt_detect_viewpager_swipe_out:
                intent = new Intent(activity, DetectViewPagerSwipeOutActivity.class);
                break;
            case R.id.bt_view_pager_tablayout:
                intent = new Intent(activity, ViewPagerWithTabLayoutActivity.class);
                break;
            case R.id.bt_detect_viewpager_swipe_out_2:
                intent = new Intent(activity, ViewPagerSwipeOut2Activity.class);
                break;
            case R.id.bt_lockable_viewpager:
                intent = new Intent(activity, LockableViewPagerActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
