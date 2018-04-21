package vn.loitp.app.activity.animation.shadowviewhelper;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.shadowviewhelper.ShadowProperty;
import vn.loitp.views.shadowviewhelper.ShadowViewDrawable;

import static vn.loitp.core.utilities.LDisplayUtils.dip2px;

//https://github.com/wangjiegulu/ShadowViewHelper?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=1884
public class ShadowViewHelperActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAllSide();
        setCustomSide();
        setCustomSide2();
    }

    private void setAllSide() {
        // all side shadow
        ShadowProperty sp = new ShadowProperty()
                .setShadowColor(0x77000000)
                .setShadowDy(dip2px(this, 0.5f))
                .setShadowRadius(dip2px(this, 3))
                .setShadowSide(ShadowProperty.ALL);
        ShadowViewDrawable sd = new ShadowViewDrawable(sp, Color.WHITE, 0, 0);
        LinearLayout ll0 = (LinearLayout) findViewById(R.id.ll_0);
        ViewCompat.setBackground(ll0, sd);
        ViewCompat.setLayerType(ll0, ViewCompat.LAYER_TYPE_SOFTWARE, null);
    }

    private void setCustomSide() {
        // only all sides except top shadow
        ShadowProperty sp = new ShadowProperty()
                .setShadowColor(0x77ff0000)
                .setShadowDy(dip2px(this, 0.5f))
                .setShadowRadius(dip2px(this, 3))
                .setShadowSide(ShadowProperty.LEFT | ShadowProperty.RIGHT | ShadowProperty.BOTTOM);
        ShadowViewDrawable sd = new ShadowViewDrawable(sp, Color.TRANSPARENT, 0, 0);
        LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll_1);
        ViewCompat.setBackground(ll1, sd);
        ViewCompat.setLayerType(ll1, ViewCompat.LAYER_TYPE_SOFTWARE, null);
    }

    private void setCustomSide2() {
        // only all sides except top shadow
        ShadowProperty sp = new ShadowProperty()
                .setShadowColor(0x7700ff00)
                .setShadowDy(dip2px(this, 0.5f))
                .setShadowRadius(dip2px(this, 3))
                .setShadowSide(ShadowProperty.RIGHT | ShadowProperty.BOTTOM);
        ShadowViewDrawable sd = new ShadowViewDrawable(sp, Color.TRANSPARENT, 0, 0);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        ViewCompat.setBackground(iv, sd);
        ViewCompat.setLayerType(iv, ViewCompat.LAYER_TYPE_SOFTWARE, null);
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
        return R.layout.activity_shadowview_helper;
    }
}
