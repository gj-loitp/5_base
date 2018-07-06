package vn.loitp.app.activity.customviews.ldebugview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

public class LDebugViewActivity extends BaseFontActivity implements OnClickListener {
    private Button btStart;
    private Button btStop;
    private Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btStart = (Button) findViewById(R.id.bt_start);
        btStop = (Button) findViewById(R.id.bt_stop);
        btSend = (Button) findViewById(R.id.bt_send);
        btStart.setOnClickListener(this);
        btStop.setOnClickListener(this);
        btSend.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LDebug.checkPermission(activity, requestCode, resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_l_debugview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                LDebug.init(activity);
                btStop.setEnabled(true);
                btSend.setEnabled(true);
                break;
            case R.id.bt_stop:
                LDebug.stop();
                btStop.setEnabled(false);
                btSend.setEnabled(false);
                break;
            case R.id.bt_send:
                LDebug.log("fuck " + System.currentTimeMillis());
                break;
        }
    }
}
