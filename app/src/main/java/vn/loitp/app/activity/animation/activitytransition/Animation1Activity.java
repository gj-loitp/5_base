package vn.loitp.app.activity.animation.activitytransition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.data.ActivityData;

public class Animation1Activity extends BaseFontActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_no_anim).setOnClickListener(this);
        findViewById(R.id.bt_system_default).setOnClickListener(this);
        findViewById(R.id.bt_slide_left).setOnClickListener(this);
        findViewById(R.id.bt_slide_right).setOnClickListener(this);
        findViewById(R.id.bt_slide_down).setOnClickListener(this);
        findViewById(R.id.bt_slide_up).setOnClickListener(this);
        findViewById(R.id.bt_fade).setOnClickListener(this);
        findViewById(R.id.bt_zoom).setOnClickListener(this);
        findViewById(R.id.bt_windmill).setOnClickListener(this);
        findViewById(R.id.bt_diagonal).setOnClickListener(this);
        findViewById(R.id.bt_spin).setOnClickListener(this);
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
        return R.layout.activity_animation_1;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, Animation2Activity.class);
        startActivity(intent);
        switch (v.getId()) {
            case R.id.bt_no_anim:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT);
                LActivityUtil.transActivityNoAniamtion(activity);
                break;
            case R.id.bt_system_default:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT);
                break;
            case R.id.bt_slide_left:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT);
                LActivityUtil.slideLeft(activity);
                break;
            case R.id.bt_slide_right:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDERIGHT);
                LActivityUtil.slideRight(activity);
                break;
            case R.id.bt_slide_down:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDEDOWN);
                LActivityUtil.slideDown(activity);
                break;
            case R.id.bt_slide_up:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP);
                LActivityUtil.slideUp(activity);
                break;
            case R.id.bt_fade:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_FADE);
                LActivityUtil.fade(activity);
                break;
            case R.id.bt_zoom:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_ZOOM);
                LActivityUtil.zoom(activity);
                break;
            case R.id.bt_windmill:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL);
                LActivityUtil.windmill(activity);
                break;
            case R.id.bt_diagonal:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL);
                LActivityUtil.diagonal(activity);
                break;
            case R.id.bt_spin:
                ActivityData.Companion.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SPIN);
                LActivityUtil.spin(activity);
                break;
        }
    }
}
