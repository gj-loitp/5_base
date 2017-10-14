package vn.loitp.app.activity.customviews.textview.scoretext;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import vn.loitp.app.base.BaseActivity;
import vn.loitp.livestar.R;

public class ScoreTextViewActivity extends BaseActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
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
}
