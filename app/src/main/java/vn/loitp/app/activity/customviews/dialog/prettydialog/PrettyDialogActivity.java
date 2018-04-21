package vn.loitp.app.activity.customviews.dialog.prettydialog;

import android.app.Activity;
import android.graphics.Typeface;
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
    private PrettyDialog prettyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_show_1).setOnClickListener(this);
        findViewById(R.id.bt_show_2).setOnClickListener(this);
        findViewById(R.id.bt_show_3).setOnClickListener(this);
        findViewById(R.id.bt_show_4).setOnClickListener(this);
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
            case R.id.bt_show_4:
                show4();
                break;
        }
    }

    private void show1() {
        prettyDialog = new PrettyDialog(activity);
        prettyDialog.setTitle("PrettyDialog Title")
                .setMessage("PrettyDialog Message")
                .show();
    }

    private void show2() {
        prettyDialog = new PrettyDialog(activity);
        prettyDialog.setTitle("PrettyDialog Title")
                .setMessage("PrettyDialog Message")
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.pdlg_color_green)
                .setIconCallback(new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        LToast.show(activity, "onClick setIconCallback");
                        prettyDialog.cancel();
                    }
                })
                .show();
    }

    private void show3() {
        prettyDialog = new PrettyDialog(activity);
        prettyDialog.setTitle("PrettyDialog Title")
                .setMessage("PrettyDialog Message")
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.pdlg_color_green)
                .setIconCallback(new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        LToast.show(activity, "onClick setIconCallback");
                    }
                })
                // OK button
                .addButton(
                        "OK",                    // button text
                        R.color.pdlg_color_white,        // button text color
                        R.color.pdlg_color_green,        // button background color
                        new PrettyDialogCallback() {        // button OnClick listener
                            @Override
                            public void onClick() {
                                LToast.show(activity, "onClick OK");
                                prettyDialog.cancel();
                            }
                        }
                )
                //Cancel button
                .addButton(
                        "Cancel",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                LToast.show(activity, "onClick Cancel");
                                prettyDialog.cancel();
                            }
                        }
                )
                // 3rd button
                .addButton(
                        "Option 3",
                        R.color.pdlg_color_black,
                        R.color.pdlg_color_gray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                LToast.show(activity, "onClick Option 3");
                                prettyDialog.cancel();
                            }
                        }
                )
                .show();
    }

    private void show4() {
        prettyDialog = new PrettyDialog(activity);
        prettyDialog
                .setTitle("PrettyDialog Title")
                .setTitleColor(R.color.pdlg_color_blue)
                .setMessage("By agreeing to our terms and conditions, you agree to our terms and conditions.")
                .setMessageColor(R.color.pdlg_color_gray)
                .setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/OpenSans-Bold.ttf"))
                .setAnimationEnabled(true)
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.pdlg_color_green)
                .setIconCallback(new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        LToast.show(activity, "onClick setIconCallback");
                    }
                })
                // OK button
                .addButton(
                        "OK",                    // button text
                        R.color.pdlg_color_white,        // button text color
                        R.color.pdlg_color_green,        // button background color
                        new PrettyDialogCallback() {        // button OnClick listener
                            @Override
                            public void onClick() {
                                LToast.show(activity, "onClick OK");
                                prettyDialog.cancel();
                            }
                        }
                )
                //Cancel button
                .addButton(
                        "Cancel",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                LToast.show(activity, "onClick Cancel");
                                prettyDialog.cancel();
                            }
                        }
                )
                // 3rd button
                .addButton(
                        "Option 3",
                        R.color.pdlg_color_black,
                        R.color.pdlg_color_gray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                LToast.show(activity, "onClick Option 3");
                                prettyDialog.cancel();
                            }
                        }
                )
                .show();
    }

    @Override
    protected void onDestroy() {
        if (prettyDialog != null) {
            prettyDialog.cancel();
        }
        super.onDestroy();
    }
}
