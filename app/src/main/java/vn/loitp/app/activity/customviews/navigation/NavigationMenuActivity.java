package vn.loitp.app.activity.customviews.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.navigation.arcnavigationview.ArcNavigationViewActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery.AndroidAdvanceImageGalleryActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidbeginnerimagegallery.AndroidBeginnerImageGalleryActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed.AndroidExpandableNewsFreedActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidinfinitelistwithloadmore.AndroidInfiniteListWithLoadMoreActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidnavigationdrawer.AndroidNavigationDrawerActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidtinderswipe.AndroidTinderSwipeActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class NavigationMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_arc_navigation).setOnClickListener(this);
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
        return R.layout.activity_menu_navigation_view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_arc_navigation:
                intent = new Intent(activity, ArcNavigationViewActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
