package vn.loitp.app.activity.customviews.button.buttonloading;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.views.button.buttonloading.ButtonLoading;

import loitp.basemaster.R;

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
                LLog.INSTANCE.d(getTAG(), "onClick");
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
                LLog.INSTANCE.d(getTAG(), "onStart");
                tv.setText("onStart");
            }

            @Override
            public void onFinish() {
                LLog.INSTANCE.d(getTAG(), "onFinish");
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
