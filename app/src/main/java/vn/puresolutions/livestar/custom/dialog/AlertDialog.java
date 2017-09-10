package vn.puresolutions.livestar.custom.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by khanh on 4/10/16.
 */
public class AlertDialog extends ConfirmDialog {

    public AlertDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnPositive.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
    }

    public void setButton(int resId) {
        setNegativeButton(resId);
    }

    public void setButton(int resId, OnClickListener onClickListener) {
        setNegativeButton(resId, onClickListener);
    }
}
