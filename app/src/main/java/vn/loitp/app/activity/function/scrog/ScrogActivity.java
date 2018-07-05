package vn.loitp.app.activity.function.scrog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.scrog.Scrog;

//https://github.com/Smyshliaiev/scrog?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=1468
public class ScrogActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btStart = (Button) findViewById(R.id.bt_start);
        Button btStop = (Button) findViewById(R.id.bt_stop);
        Button btSend = (Button) findViewById(R.id.bt_send);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scrog.init(activity);
                btStop.setEnabled(true);
                btSend.setEnabled(true);
            }
        });
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scrog.destroy();
                btStop.setEnabled(false);
                btSend.setEnabled(false);
            }
        });
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scrog.i("text " + System.currentTimeMillis());
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_scrog;
    }
}
