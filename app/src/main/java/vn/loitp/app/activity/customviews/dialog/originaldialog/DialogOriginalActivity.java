package vn.loitp.app.activity.customviews.dialog.originaldialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;

public class DialogOriginalActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_show_1).setOnClickListener(this);
        findViewById(R.id.bt_show_2).setOnClickListener(this);
        findViewById(R.id.bt_show_3).setOnClickListener(this);
        findViewById(R.id.bt_show_list).setOnClickListener(this);
        findViewById(R.id.bt_progress_dialog).setOnClickListener(this);
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
        return R.layout.activity_dialog_original;
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
            case R.id.bt_show_list:
                showList();
                break;
            case R.id.bt_progress_dialog:
                showProgress();
                break;
        }
    }

    private void show1() {
        LDialogUtil.showDialog1(activity, "Title", "Msg", "Button 1", new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                ToastUtils.showShort("Click 1");
            }
        });
    }

    private void show2() {
        LDialogUtil.showDialog2(activity, "Title", "Msg", "Button 1", "Button 2", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                ToastUtils.showShort("Click 1");
            }

            @Override
            public void onClick2() {
                ToastUtils.showShort("Click 2");
            }
        });
    }

    private void show3() {
        LDialogUtil.showDialog3(activity, "Title", "Msg", "Button 1", "Button 2", "Button 3", new LDialogUtil.Callback3() {
            @Override
            public void onClick1() {
                ToastUtils.showShort("Click 1");
            }

            @Override
            public void onClick2() {
                ToastUtils.showShort("Click 2");
            }

            @Override
            public void onClick3() {
                ToastUtils.showShort("Click 3");
            }
        });
    }

    private void showList() {
        int size = 50;
        String arr[] = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = "Item " + i;
        }
        LDialogUtil.showDialogList(activity, "Title", arr, new LDialogUtil.CallbackList() {
            @Override
            public void onClick(int position) {
                ToastUtils.showShort("Click position " + position + ", item: " + arr[position]);
            }
        });
    }

    private void showProgress() {
        ProgressDialog progressDialog = LDialogUtil.showProgressDialog(activity, 100, "Tile", "Message", false, ProgressDialog.STYLE_HORIZONTAL, null, null);
        new AsyncTask<Void, Integer, Void>() {
            int i = 0;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                for (i = 0; i < progressDialog.getMax(); i++) {
                    publishProgress(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressDialog.setProgress(values[0]);

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }
}
