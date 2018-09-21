package vn.loitp.app.activity.customviews.dialog.swipeawaydialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.dialog.swipeawaydialog.support.v4.SwipeAwayDialogFragment;

public class SADilog extends SwipeAwayDialogFragment {
    private final String TAG = getClass().getSimpleName();
    public static String KEY;
    public final static int KEY_1 = 1;
    public final static int KEY_2 = 2;
    public final static int KEY_3 = 3;
    public final static int KEY_4 = 4;
    public final static int KEY_5 = 5;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int key = Constants.NOT_FOUND;
        if (bundle != null) {
            key = bundle.getInt(KEY);
        }
        LLog.d(TAG, "key " + key);
        switch (key) {
            case KEY_1:
                return show1();
            case KEY_2:
                return show2();
            case KEY_3:
                return show3();
            case KEY_4:
                return showList();
            case KEY_5:
                return showProgress();
            default:
                return show1();
        }
    }

    private AlertDialog show1() {
        return LDialogUtil.showDialog1(getActivity(), "Title", "Msg", "Button 1", new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                ToastUtils.showShort("Click 1");
            }
        });
    }

    private AlertDialog show2() {
        return LDialogUtil.showDialog2(getActivity(), "Title", "Msg", "Button 1", "Button 2", new LDialogUtil.Callback2() {
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

    private AlertDialog show3() {
        return LDialogUtil.showDialog3(getActivity(), "Title", "Msg", "Button 1", "Button 2", "Button 3", new LDialogUtil.Callback3() {
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

    private AlertDialog showList() {
        int size = 50;
        String arr[] = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = "Item " + i;
        }
        return LDialogUtil.showDialogList(getActivity(), "Title", arr, new LDialogUtil.CallbackList() {
            @Override
            public void onClick(int position) {
                ToastUtils.showShort("Click position " + position + ", item: " + arr[position]);
            }
        });
    }

    private AlertDialog showProgress() {
        ProgressDialog progressDialog = LDialogUtil.showProgressDialog(getActivity(), 100, "Title", "Message", false, ProgressDialog.STYLE_HORIZONTAL, null, null);
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
        return progressDialog;
    }
}
