package vn.loitp.app.activity.customviews.dialog.prettydialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.dialog.prettydialog.PrettyDialog;
import vn.loitp.views.dialog.prettydialog.PrettyDialogCallback;

//https://github.com/mjn1369/PrettyDialog
public class PrettyDialogActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_show_1).setOnClickListener(this);
        findViewById(R.id.bt_show_2).setOnClickListener(this);
        findViewById(R.id.bt_show_3).setOnClickListener(this);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_dialog_pretty;
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
            case R.id.bt_show_3:
                show3();
                break;
        }
    }

    private void show1() {
        new PrettyDialog(this)
                .setTitle("PrettyDialog Title")
                .setMessage("PrettyDialog Message")
                .show();
    }

    private void show2() {
        new PrettyDialog(this)
                .setTitle("PrettyDialog Title")
                .setMessage("PrettyDialog Message")
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.pdlg_color_green)
                .setIconCallback(new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        LToast.show(activity, "onClick setIconCallback");
                    }
                })
                .show();
    }

    private void show3() {

    }
}
