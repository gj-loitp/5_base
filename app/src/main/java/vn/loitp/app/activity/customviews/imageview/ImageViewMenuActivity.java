package vn.loitp.app.activity.customviews.imageview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewWithScrollViewActivity;
import vn.loitp.app.activity.customviews.imageview.blurimageview.BlurImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.circleimageview.CircleImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.circularroundrectimageview.CircularRoundRectImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview.ContinuousScrollableImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview.FidgetSpinnerImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.panoramaimageview.PanoramaImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.scrollparallaximageview.ScrollParallaxImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.strectchyimageview.StrectchyImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.touchimageview.TouchImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.zoomimageview.ZoomImageViewActivity;

public class ImageViewMenuActivity extends BaseFontActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_blur_imageview).setOnClickListener(this);
        findViewById(R.id.bt_cirle_imageview).setOnClickListener(this);
        findViewById(R.id.bt_stretchy_imageview).setOnClickListener(this);
        findViewById(R.id.bt_touch_imageview).setOnClickListener(this);
        findViewById(R.id.bt_zoom_imageview).setOnClickListener(this);
        findViewById(R.id.bt_fidgetspinner).setOnClickListener(this);
        findViewById(R.id.bt_cirlularroundrect_imageview).setOnClickListener(this);
        findViewById(R.id.bt_continuous_scrollable_imageview).setOnClickListener(this);
        findViewById(R.id.bt_scroll_parallax_imageview).setOnClickListener(this);
        findViewById(R.id.bt_panorama_imageview).setOnClickListener(this);
        findViewById(R.id.bt_big_imageview).setOnClickListener(this);
        findViewById(R.id.bt_big_imageview_with_scroll_view).setOnClickListener(this);
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
        return R.layout.activity_menu_imageview;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_blur_imageview:
                intent = new Intent(getActivity(), BlurImageViewActivity.class);
                break;
            case R.id.bt_cirle_imageview:
                intent = new Intent(getActivity(), CircleImageViewActivity.class);
                break;
            case R.id.bt_stretchy_imageview:
                intent = new Intent(getActivity(), StrectchyImageViewActivity.class);
                break;
            case R.id.bt_touch_imageview:
                intent = new Intent(getActivity(), TouchImageViewActivity.class);
                break;
            case R.id.bt_zoom_imageview:
                intent = new Intent(getActivity(), ZoomImageViewActivity.class);
                break;
            case R.id.bt_fidgetspinner:
                intent = new Intent(getActivity(), FidgetSpinnerImageViewActivity.class);
                break;
            case R.id.bt_cirlularroundrect_imageview:
                intent = new Intent(getActivity(), CircularRoundRectImageViewActivity.class);
                break;
            case R.id.bt_continuous_scrollable_imageview:
                intent = new Intent(getActivity(), ContinuousScrollableImageViewActivity.class);
                break;
            case R.id.bt_scroll_parallax_imageview:
                intent = new Intent(getActivity(), ScrollParallaxImageViewActivity.class);
                break;
            case R.id.bt_panorama_imageview:
                intent = new Intent(getActivity(), PanoramaImageViewActivity.class);
                break;
            case R.id.bt_big_imageview:
                intent = new Intent(getActivity(), BigImageViewActivity.class);
                break;
            case R.id.bt_big_imageview_with_scroll_view:
                intent = new Intent(getActivity(), BigImageViewWithScrollViewActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.INSTANCE.tranIn(getActivity());
        }
    }
}
