package vn.loitp.app.activity.customviews.textview.scoretext;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import vn.loitp.app.R;

public class ScoreTextViewActivity extends BaseFontActivity {
    private TextView tv;
    private Button bt;

    private int maxScore = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.bt);

        tv.setText("" + currentScore);

        bt.setOnClickListener(v -> {
            currentScore = 0;
            bt.setEnabled(false);
            updateScore(maxScore);
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
                    LLog.d(getTAG(), msg.obj + "");
                    if (msg.obj.equals(String.valueOf(maxScore))) {
                        LLog.d(getTAG(), "done");
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
