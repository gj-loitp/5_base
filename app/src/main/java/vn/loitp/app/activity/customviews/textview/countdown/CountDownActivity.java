package vn.loitp.app.activity.customviews.textview.countdown;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.loitp.app.activity.customviews.textview.countdown.lib.LCountDownView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.livestar.R;

public class CountDownActivity extends BaseActivity {

    private Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btStart = (Button) findViewById(R.id.bt_start);

        LCountDownView lCountDownView = (LCountDownView) findViewById(R.id.l_countdown);
        lCountDownView.setShowOrHide(false);
        lCountDownView.setCallback(new LCountDownView.Callback() {
            @Override
            public void onTick() {
                //do sth here
            }

            @Override
            public void onEnd() {
                btStart.setEnabled(true);
                lCountDownView.setShowOrHide(false);
            }
        });

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btStart.setEnabled(false);
                lCountDownView.setShowOrHide(true);
                lCountDownView.start(5);
            }
        });
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_count_down_textview;
    }
}
