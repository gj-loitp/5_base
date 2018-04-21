package vn.loitp.app.activity.customviews.edittext.biuedittext;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;

//guide https://github.com/xujinyang/BiuEditText
public class BiuEditTextActivity extends BaseActivity {
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
        return R.layout.activity_biu_editext;
    }

}
