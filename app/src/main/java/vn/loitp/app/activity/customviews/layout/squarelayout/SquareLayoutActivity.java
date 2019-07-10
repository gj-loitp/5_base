package vn.loitp.app.activity.customviews.layout.squarelayout;

import android.os.Bundle;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;

public class SquareLayoutActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return R.layout.activity_square_layout;
    }
}
