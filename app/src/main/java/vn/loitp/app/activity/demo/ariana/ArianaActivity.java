package vn.loitp.app.activity.demo.ariana;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.views.ariana.Ariana;
import vn.loitp.views.ariana.GradientAngle;

public class ArianaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView iv = (ImageView) findViewById(R.id.iv);

        iv.setBackground(Ariana.drawable(LStoreUtil.getColors()), GradientAngle.LEFT_BOTTOM_TO_RIGHT_TOP);
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
        return R.layout.activity_ariana;
    }
}
