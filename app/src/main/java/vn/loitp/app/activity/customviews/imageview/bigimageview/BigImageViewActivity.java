package vn.loitp.app.activity.customviews.imageview.bigimageview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.imageview.blurimageview.BlurImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.circleimageview.CircleImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.circularroundrectimageview.CircularRoundRectImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview.ContinuousScrollableImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview.FidgetSpinnerImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.img2ascii.Img2AsciiActivity;
import vn.loitp.app.activity.customviews.imageview.panoramaimageview.PanoramaImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.scrollparallaximageview.ScrollParallaxImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.strectchyimageview.StrectchyImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.touchimageview.TouchImageViewActivity;
import vn.loitp.app.activity.customviews.imageview.zoomimageview.ZoomImageViewActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class BigImageViewActivity extends BaseFontActivity {

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
    protected int setLayoutResourceId() {
        return R.layout.activity_big_imageview;
    }
}
