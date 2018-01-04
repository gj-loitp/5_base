package vn.loitp.app.activity.animation.activitytransition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.data.ActivityData;

public class Animation1Activity extends BaseActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_system_default).setOnClickListener(this);
        findViewById(R.id.bt_slide_left).setOnClickListener(this);
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
        return R.layout.activity_animation_1;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, Animation2Activity.class);
        startActivity(intent);
        switch (v.getId()) {
            case R.id.bt_system_default:
                ActivityData.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT);
                break;
            case R.id.bt_slide_left:
                ActivityData.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT);
                LActivityUtil.slideLeft(activity);
                break;
        }
    }
}
