package com.views.dialog.iosdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import loitp.core.R;

public class iOSDialog {
    private Dialog dialog;
    private TextView dialogButtonOk, dialogButtonNo;
    private TextView title_lbl, subtitle_lbl;
    private View separator;
    private boolean negativeExist = false;

    public iOSDialog(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.alerts_two_buttons);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initViews();
    }


    public void setPositiveListener(View.OnClickListener okListener) {
        dialogButtonOk.setOnClickListener(okListener);
    }

    public void setNegativeListener(View.OnClickListener okListener) {
        if (!negativeExist) {
            Log.e("iOSDialog", "!!! Negative button isn't visible, set it with setNegativeLabel()!!!");
        }
        dialogButtonNo.setOnClickListener(okListener);
    }

    public void show() {
        if (!negativeExist) {
            dialogButtonNo.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        title_lbl.setText(title);
    }

    public void setSubtitle(String subtitle) {
        subtitle_lbl.setText(subtitle);
    }

    public void setPositiveLabel(String positive) {
        dialogButtonOk.setText(positive);
    }

    public void setNegativeLabel(String negative) {
        negativeExist = true;
        dialogButtonNo.setText(negative);
    }

    public void setBoldPositiveLabel(boolean bold) {
        if (bold)
            dialogButtonOk.setTypeface(null, Typeface.BOLD);
        else
            dialogButtonOk.setTypeface(null, Typeface.NORMAL);
    }

    public void setTipefaces(Typeface appleFont) {
        title_lbl.setTypeface(appleFont);
        subtitle_lbl.setTypeface(appleFont);
        dialogButtonOk.setTypeface(appleFont);
        dialogButtonNo.setTypeface(appleFont);
    }


    private void initViews() {
        title_lbl = dialog.findViewById(R.id.title);
        subtitle_lbl = dialog.findViewById(R.id.subtitle);
        dialogButtonOk = dialog.findViewById(R.id.dialogButtonOK);
        dialogButtonNo = dialog.findViewById(R.id.dialogButtonNO);
        separator = dialog.findViewById(R.id.separator);
    }


}