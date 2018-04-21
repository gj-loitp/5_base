package vn.loitp.app.activity.customviews.dialog.iosdialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.views.LToast;

public class DialogIOSActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_show_1).setOnClickListener(this);
        findViewById(R.id.bt_show_2).setOnClickListener(this);
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
        return R.layout.activity_dialog_ios;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_show_1:
                show1();
                break;
            case R.id.bt_show_2:
                show2();
                break;
        }
    }

    private void show1() {
        LDialogUtil.showIOSDialog1(activity, "Allow \"Calendar\" to access your location while you use the app?", "This is a subtitle", "Allow", false, new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                LToast.show(activity, "onClick1");
            }
        });
    }

    private void show2() {
        LDialogUtil.showIOSDialog2(activity, "Allow \"Calendar\" to access your location while you use the app?", "This is a subtitle", "Don't Allow", "Allow", true, new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                LToast.show(activity, "onClick1");
            }

            @Override
            public void onClick2() {
                LToast.show(activity, "onClick2");
            }
        });
    }
}
