package vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.imageview.fidgetspinnerimageview.FidgetSpinner;

public class FidgetSpinnerImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FidgetSpinner f = (FidgetSpinner) findViewById(R.id.fidgetspinner);
        f.setImageDrawable(R.drawable.spinner);
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
        return R.layout.activity_fidgetspinner_imageview;
    }
}
