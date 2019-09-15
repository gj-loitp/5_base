package vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.views.imageview.fidgetspinner.LFidgetSpinner;

import loitp.basemaster.R;

public class FidgetSpinnerImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LFidgetSpinner f = (LFidgetSpinner) findViewById(R.id.fidgetspinner);
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
