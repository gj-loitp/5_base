package vn.loitp.app.activity.customviews.edittext.autoresizeedittext;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

public class AutoResizeEditTextActivity extends BaseFontActivity {
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
        return R.layout.activity_autoresize_editext;
    }

}
