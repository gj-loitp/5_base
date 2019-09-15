package vn.loitp.app.activity.customviews.button.buttonloading;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.views.button.loading.LButtonLoading;

import loitp.basemaster.R;

//https://github.com/rasoulmiri/ButtonLoading
//Note: Do not use the button in LinearLayout.
public class ButtonLoadingActivity extends BaseActivity {
    private TextView tv;
    private LButtonLoading LButtonLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LButtonLoading = findViewById(R.id.bt);
        tv = findViewById(R.id.tv);
        LButtonLoading.setOnButtonLoadingListener(new LButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {
                LLog.d(getTAG(), "onClick");
                tv.setText("onClick");
                LUIUtil.INSTANCE.setDelay(3000, new Runnable() {
                    @Override
                    public void run() {
                        LButtonLoading.setProgress(false);
                    }
                });
            }

            @Override
            public void onStart() {
                LLog.d(getTAG(), "onStart");
                tv.setText("onStart");
            }

            @Override
            public void onFinish() {
                LLog.d(getTAG(), "onFinish");
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
