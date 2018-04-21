package vn.loitp.app.activity.customviews.textview.scrollnumber;

import android.app.Activity;
import android.os.Bundle;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.views.textview.scrollnumber.lib.MultiScrollNumber;

public class ScrollNumberActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiScrollNumber scrollNumber = (MultiScrollNumber) findViewById(R.id.scroll_number);

        scrollNumber.setTextColors(new int[]{R.color.red01, R.color.orange01, R.color.blue01, R.color.green01, R.color.purple01});
        //scrollNumber.setTextSize(64);

        //scrollNumber.setNumber(64, 2048);
        //scrollNumber.setInterpolator(new DecelerateInterpolator());


        scrollNumber.setScrollVelocity(20);
        scrollNumber.setNumber(20.48);
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
        return R.layout.activity_scroll_number;
    }

}
