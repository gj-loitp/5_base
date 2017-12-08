package vn.loitp.app.activity.customviews.layout.swiperefreshlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withscrollview.SwipeRefreshLayoutActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LUIUtil;

public class SwipeRefreshLayoutMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_with_scroll_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SwipeRefreshLayoutActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
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
        return R.layout.activity_swipe_refresh_menu_layout;
    }
}
