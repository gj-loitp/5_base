package vn.puresolutions.livestar.custom.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import vn.puresolutions.livestar.R;

/**
 * Created by khanh on 4/10/16.
 */
public class ConfirmDialog extends LSDialog {

    private int msgId;
    private String message;

    public ConfirmDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);

        TextView tvMessage = (TextView) findViewById(R.id.ls_confirmDialog_tvMessage);
        if (TextUtils.isEmpty(message)) {
            tvMessage.setText(msgId);
        } else {
            tvMessage.setText(message);
        }
    }

    public void setMessage(int msgId) {
        this.msgId = msgId;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
