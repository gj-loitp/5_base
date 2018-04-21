package vn.loitp.app.activity.customviews.layout.autolinearlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.layout.autolinearlayout.AutoLinearLayout;

//read more
//https://github.com/AlbertGrobas/AutoLinearLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=1852
public class AutoLinearLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Java
        /*AutoLinearLayout l2 = new AutoLinearLayout(this);
        l2.setOrientation(AutoLinearLayout.VERTICAL);
        l2.setGravity(Gravity.CENTER);
        //add other views
        parent.addView(l2);*/
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
        return R.layout.activity_auto_linear_layout;
    }
}
