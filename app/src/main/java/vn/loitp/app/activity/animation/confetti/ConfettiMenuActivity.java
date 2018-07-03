package vn.loitp.app.activity.animation.confetti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.animation.activitytransition.Animation1Activity;
import vn.loitp.app.activity.animation.animationview.AnimationViewActivity;
import vn.loitp.app.activity.animation.expectanim.ExpectAnimActivity;
import vn.loitp.app.activity.animation.flyschool.FlySchoolActivity;
import vn.loitp.app.activity.animation.overscroll.OverScrollActivity;
import vn.loitp.app.activity.animation.shadowviewhelper.ShadowViewHelperActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

//https://github.com/jinatonic/confetti
public class ConfettiMenuActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_0).setOnClickListener(this);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        findViewById(R.id.bt_4).setOnClickListener(this);
        findViewById(R.id.bt_5).setOnClickListener(this);
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
        return R.layout.activity_menu_confetti;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_0:
                intent = new Intent(activity, FallingConfettiFromTopActivity.class);
                break;
            case R.id.bt_1:
                intent = new Intent(activity, FallingConfettiFromPointActivity.class);
                break;
            case R.id.bt_2:
                intent = new Intent(activity, AnimationViewActivity.class);
                break;
            case R.id.bt_3:
                intent = new Intent(activity, AnimationViewActivity.class);
                break;
            case R.id.bt_4:
                intent = new Intent(activity, AnimationViewActivity.class);
                break;
            case R.id.bt_5:
                intent = new Intent(activity, AnimationViewActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
