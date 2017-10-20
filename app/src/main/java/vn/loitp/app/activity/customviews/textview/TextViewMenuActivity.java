package vn.loitp.app.activity.customviews.textview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.textview.countdown.CountDownActivity;
import vn.loitp.app.activity.customviews.textview.scoretext.ScoreTextViewActivity;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class TextViewMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_score_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ScoreTextViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_countdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CountDownActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
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
        return R.layout.activity_menu_textview;
    }
}
