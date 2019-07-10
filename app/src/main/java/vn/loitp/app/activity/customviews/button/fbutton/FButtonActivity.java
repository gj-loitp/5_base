package vn.loitp.app.activity.customviews.button.fbutton;

import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.utils.util.ToastUtils;

import loitp.basemaster.R;

//guide https://github.com/hoang8f/android-flat-button
public class FButtonActivity extends BaseFontActivity {

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
