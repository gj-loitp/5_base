package vn.loitp.app.activity.customviews.button.fbutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.utils.util.ToastUtils;

//guide https://github.com/hoang8f/android-flat-button
public class FButtonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.primary_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("onClick");
            }
        });
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
        return R.layout.activity_f_button;
    }
}
