package vn.puresolutions.livestar.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;

/**
 * @author hoangphu
 * @since 9/8/16
 */
public abstract class IRoomDialog extends Dialog {

    @BindView(R.id.dialogRoom_tvTitle)
    TextView tvTitle;

    IRoomDialog(Context context) {
        super(context, R.style.full_screen_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContainerLayout());
        ViewGroup lnlContainer = (ViewGroup)findViewById(R.id.dialogRoom_lnlContainer);
        LayoutInflater.from(getContext()).inflate(getLayoutId(), lnlContainer, true);
        ButterKnife.bind(this);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
    }

    @Override
    public void setTitle(int titleId) {
        tvTitle.setText(getContext().getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    @OnClick({R.id.dialogRoom_imgClose, R.id.dialogRoom_btnCancel})
    void onCloseButtonClick() {
        dismiss();
    }

    @OnClick(R.id.dialogRoom_btnOk)
    void onOkButtonClick() {
        dismiss();
    }

    protected int getContainerLayout() {
        return R.layout.dialog_room;
    }

    public abstract int getLayoutId();
}
