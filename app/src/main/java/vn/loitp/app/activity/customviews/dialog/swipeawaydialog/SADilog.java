package vn.loitp.app.activity.customviews.dialog.swipeawaydialog;

import android.app.Dialog;
import android.os.Bundle;

import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.dialog.swipeawaydialog.support.v4.SwipeAwayDialogFragment;

public class SADilog extends SwipeAwayDialogFragment {
    private final String TAG = getClass().getSimpleName();
    public static String KEY;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int key = Constants.NOT_FOUND;
        if (bundle != null) {
            key = bundle.getInt(KEY);
        }
        LLog.d(TAG, "key " + key);
        return LDialogUtil.showDialog1(getActivity(), "Title", "Msg", "Button 1", new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                ToastUtils.showShort("Click 1");
            }
        });
    }
}
