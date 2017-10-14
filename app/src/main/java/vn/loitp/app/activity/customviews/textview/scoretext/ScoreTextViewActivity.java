package vn.loitp.app.activity.customviews.textview.scoretext;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LLog;
import vn.loitp.livestar.R;

public class ScoreTextViewActivity extends BaseActivity {
    private TextView tv;
    private Button bt;

    private int maxScore = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        bt = (Button) findViewById(R.id.bt);

        tv.setText("" + currentScore);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentScore = 0;
                bt.setEnabled(false);
                updateScore(maxScore);
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
        return R.layout.activity_score_text;
    }

    private int currentScore = 0;
    public static final int KEY_UPDATE_SCORE = 1;
    public static final int DELTA_T = 20;//msecond
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KEY_UPDATE_SCORE:
                    tv.setText((String) msg.obj);
                    LLog.d(TAG, msg.obj + "");
                    if (((String) msg.obj).equals(String.valueOf(maxScore))) {
                        LLog.d(TAG, "done");
                        bt.setEnabled(true);
                    }
                    break;
            }
        }
    };

    private void updateScore(int totalScore) {
        for (int i = currentScore; i <= totalScore; i++) {
            handler.sendMessageDelayed(handler.obtainMessage(KEY_UPDATE_SCORE, "" + i), (i - currentScore) * DELTA_T);
        }
    }

}
