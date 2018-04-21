package vn.loitp.app.activity.customviews.ariana.tv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.views.ariana.Ariana;
import vn.loitp.views.ariana.GradientAngle;

//https://github.com/akshay2211/Ariana?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6235
public class ArianaTextViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = (TextView) findViewById(R.id.tv);
        Ariana.setGradient(tv, LStoreUtil.getColors(), GradientAngle.LEFT_BOTTOM_TO_RIGHT_TOP);
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
        return R.layout.activity_ariana_tv;
    }
}
