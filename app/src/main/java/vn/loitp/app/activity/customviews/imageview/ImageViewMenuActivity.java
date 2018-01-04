package vn.loitp.app.activity.customviews.imageview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.imageview.blurimageview.BlurImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.circleimageview.CircleImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.circularroundrectimageview.CircularRoundRectImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview.FidgetSpinnerImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.img2ascii.Img2AsciiActivity;
import vn.loitp.app.activity.customviews.imageview.strectchyimageview.StrectchyImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.touchimageview.TouchImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.zoomimageview.ZoomImageViewActivity;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;

public class ImageViewMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_blur_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BlurImageViewActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_cirle_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CircleImageViewActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_stretchy_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, StrectchyImageViewActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_touch_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TouchImageViewActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_zoom_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ZoomImageViewActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_fidgetspinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FidgetSpinnerImageViewActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_cirlularroundrect_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CircularRoundRectImageViewActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_img2ascii).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Img2AsciiActivity.class);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_imageview;
    }
}
