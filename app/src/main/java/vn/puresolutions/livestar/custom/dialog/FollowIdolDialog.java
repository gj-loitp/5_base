package vn.puresolutions.livestar.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;

/**
 * @author hoangphu
 * @since 9/8/16
 */
public class FollowIdolDialog extends Dialog {

    public FollowIdolDialog(Context context) {
        super(context, R.style.full_screen_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_follow_idol);
        ButterKnife.bind(this);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
    }

    @OnClick(R.id.dialogRoom_btnOk)
    void onOkButtonClick() {
        dismiss();
    }
}
