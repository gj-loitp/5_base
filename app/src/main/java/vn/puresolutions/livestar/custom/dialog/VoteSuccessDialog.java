package vn.puresolutions.livestar.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;

/**
 * Created by Anhdv on 7/12/2017.
 */

public class VoteSuccessDialog extends Dialog {
    @BindView(R.id.dialogVote_Success)
    TextView tvSuccess;
    @BindView(R.id.dialogVote_OK)
    Button btnOK;
    @BindView(R.id.dialogVoteSuccess_imgClose)
    ImageView ivClose;
    public VoteSuccessDialog(Context context) {
        super(context,R.style.full_screen_dialog);
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
    protected int getContainerLayout() {
        return R.layout.dialog_success_vote;
    }
    public int getLayoutId() {
        return R.layout.dialog_success_vote;
    }
    @OnClick(R.id.dialogVote_OK)
    void onCloseDialogOK() {
        dismiss();
    }
    @OnClick(R.id.dialogVoteSuccess_imgClose)
    void onCloseDialogClose() {
        dismiss();
    }
}
