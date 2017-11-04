package vn.loitp.app.activity.ads.admobbanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.loitp.app.activity.animation.animationview.AnimationViewActivity;
import vn.loitp.app.activity.animation.overscroll.OverScrollActivity;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LStoreUtil;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class AdmobBannerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = (TextView) findViewById(R.id.tv);
        String poem = LStoreUtil.readTxtFromRawFolder(activity, R.raw.loitp);
        tv.setText(poem);
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
        return R.layout.activity_admob_banner;
    }
}
