package vn.puresolutions.livestar.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.OnClick;
import vn.puresolutions.livestar.R;

/**
 * Created by khanh on 4/7/16.
 */
public abstract class LSDialog extends Dialog {

    private LinearLayout container;
    protected Button btnNegative;
    protected Button btnPositive;
    protected View divider;

    private boolean hideCloseButton = false;
    private int positiveResId = 0;
    private int negativeResId = 0;
    private OnClickListener onPositiveClickListener;
    private OnClickListener onNegativeClickListener;

    public LSDialog(Context context) {
        super(context);
    }

    public LSDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LSDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.dialog_base);

        container = (LinearLayout) findViewById(R.id.dialogBase_container);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View content = inflater.inflate(layoutResID, container, false);
        container.addView(content, 0);

        btnPositive = (Button) findViewById(R.id.ls_dialogBase_btnPositive);
        btnNegative = (Button) findViewById(R.id.ls_dialogBase_btnNegative);
        divider = findViewById(R.id.dialogBase_vwDivider);
        if (positiveResId > 0) {
            btnPositive.setText(positiveResId);
            btnPositive.setOnClickListener(v -> {
                if (onPositiveClickListener != null) {
                    onPositiveClickListener.onClick(LSDialog.this);
                }
                dismiss();
            });
        }

        if (negativeResId > 0) {
            btnNegative.setText(negativeResId);
            btnNegative.setOnClickListener(v -> {
                if (onNegativeClickListener != null) {
                    onNegativeClickListener.onClick(LSDialog.this);
                }
                dismiss();
            });
        } else {
            btnNegative.setVisibility(View.GONE);
            findViewById(R.id.dialogBase_vwDivider).setVisibility(View.GONE);
        }

        View imgClose = findViewById(R.id.ls_dialogBase_imgClose);
        imgClose.setOnClickListener(v -> cancel());
        imgClose.setVisibility(hideCloseButton ? View.INVISIBLE : View.VISIBLE);
    }

    public LSDialog setPositiveButton(int resId, OnClickListener onClickListener) {
        this.positiveResId = resId;
        this.onPositiveClickListener = onClickListener;
        return this;
    }

    public LSDialog setPositiveButton(int resId) {
        return setPositiveButton(resId, null);
    }

    public LSDialog setNegativeButton(int resId, OnClickListener onClickListener) {
        this.negativeResId = resId;
        this.onNegativeClickListener = onClickListener;
        return this;
    }

    public LSDialog setNegativeButton(int resId) {
        return setNegativeButton(resId, null);
    }

    public LSDialog hideCloseButton() {
        hideCloseButton = true;
        return this;
    }

    public interface OnClickListener {
        void onClick(LSDialog dialog);
    }
}
