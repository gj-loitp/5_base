package vn.loitp.app.activity.donation;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.loitp.donate.FrmDonate;
import vn.loitp.core.utilities.LScreenUtil;

public class DonationActivity extends BaseFontActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LScreenUtil.addFragment(activity, R.id.fl_container, new FrmDonate(), false);
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
        return R.layout.activity_more;
    }
}
