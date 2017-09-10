package vn.puresolutions.livestar.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import vn.puresolutions.livestar.R;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/17/2015
 */
public class ProgressDialog extends Dialog {

    private String message;

    private long startTime;
    private long delay = 1000;

    public ProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        TextView txtMessage = (TextView) findViewById(R.id.progressDialog_tvMessage);
        txtMessage.setText(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void show() {
        super.show();
        startTime = System.currentTimeMillis();
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public void dismiss() {
        long duration = System.currentTimeMillis() - startTime;
        if (duration >= delay) {
            super.dismiss();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, delay - duration);
        }
    }
}
