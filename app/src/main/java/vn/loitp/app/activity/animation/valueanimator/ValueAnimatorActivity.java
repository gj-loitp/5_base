package vn.loitp.app.activity.animation.valueanimator;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;

//https://viblo.asia/p/custom-view-trong-android-gGJ59br9KX2
public class ValueAnimatorActivity extends BaseFontActivity {
    private TextView tvDebug;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvDebug = (TextView) findViewById(R.id.tv_debug);
        view = (View) findViewById(R.id.view);
        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnim();
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
    protected int setLayoutResourceId() {
        return R.layout.activity_value_animator;
    }

    private ValueAnimator valueAnimator;

    private void startAnim() {
        int min = 0;
        int max = 500;
        float range = max - min;
        int duration = 2000;
        valueAnimator = ValueAnimator.ofInt(min, max);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        float spaceW = (LScreenUtil.getScreenWidth() - view.getWidth()) / range;
        float spaceH = (LScreenUtil.getScreenHeight() - LScreenUtil.getStatusBarHeight(activity) - LScreenUtil.getBottomBarHeight(activity) - view.getHeight()) / range;
        LLog.d(TAG, "view " + view.getWidth() + "x" + view.getHeight() + " -> " + spaceW + " - " + spaceH);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                tvDebug.setText("onAnimationUpdate: " + value + " -> " + spaceW * value + " x " + spaceH * value);
                updateUI(view, spaceW * value, spaceH * value);
            }
        });
        valueAnimator.start();
    }

    private void updateUI(View view, float posX, float posY) {
        view.setTranslationX(posX);
        view.setTranslationY(posY);
    }

    @Override
    protected void onDestroy() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        super.onDestroy();
    }
}
