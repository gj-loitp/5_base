package vn.loitp.app.activity.customviews.placeholderview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery.AndroidAdvanceImageGalleryActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidbeginnerimagegallery.AndroidBeginnerImageGalleryActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed.AndroidExpandableNewsFreedActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidinfinitelistwithloadmore.AndroidInfiniteListWithLoadMoreActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidnavigationdrawer.AndroidNavigationDrawerActivity;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidtinderswipe.AndroidTinderSwipeActivity;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;

public class PlaceHolderViewMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_android_navigation_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AndroidNavigationDrawerActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_android_beginner_image_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AndroidBeginnerImageGalleryActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_android_advance_image_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AndroidAdvanceImageGalleryActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_android_expandable_news_feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AndroidExpandableNewsFreedActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_android_tinder_swipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AndroidTinderSwipeActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_android_infinite_list_with_load_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AndroidInfiniteListWithLoadMoreActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
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
    protected int setLayoutResourceId() {
        return R.layout.activity_placeholderview_menu;
    }
}
