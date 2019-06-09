package vn.loitp.app.activity.customviews.button.buttonloading;

import android.os.Bundle;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.button.buttonloading.ButtonLoading;

//https://github.com/rasoulmiri/ButtonLoading
//Note: Do not use the button in LinearLayout.
public class ButtonLoadingActivity extends BaseActivity {
    private TextView tv;
    private ButtonLoading buttonLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonLoading = (ButtonLoading) findViewById(R.id.bt);
        tv = (TextView) findViewById(R.id.tv);
        buttonLoading.setOnButtonLoadingListener(new ButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {
                LLog.INSTANCE.d(TAG, "onClick");
                tv.setText("onClick");
                LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
                    @Override
                    public void doAfter(int mls) {
                        buttonLoading.setProgress(false);
                    }
                });
            }

            @Override
            public void onStart() {
                LLog.INSTANCE.d(TAG, "onStart");
                tv.setText("onStart");
            }

            @Override
            public void onFinish() {
                LLog.INSTANCE.d(TAG, "onFinish");
                tv.setText("onFinish");
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
        return R.layout.activity_button_loading;
    }
}
