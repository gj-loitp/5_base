package vn.loitp.app.activity.animation.expectanim;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.base.BaseFragment;
import com.core.utilities.LScreenUtil;

import loitp.basemaster.R;

//https://github.com/florent37/ExpectAnim/?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5335
public class ExpectAnimActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFrm(new FrmMenu(), false);
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
        return R.layout.activity_expect_anim;
    }

    public void addFrm(BaseFragment baseFragment, boolean isAddToBackStack) {
        if (baseFragment == null) {
            return;
        }
        LScreenUtil.addFragment(activity, R.id.fl_container, baseFragment, isAddToBackStack);
    }
}
