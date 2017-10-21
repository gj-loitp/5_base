package vn.loitp.app.activity.customviews.imageview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.imageview.circleimageview.CircleImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.strectchyimageview.StrectchyImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.touchimageview.TouchImageViewActivity;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class ImageViewMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_cirle_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CircleImageViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_stretchy_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, StrectchyImageViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_touch_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TouchImageViewActivity.class);
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
        return R.layout.activity_menu_imageview;
    }
}
