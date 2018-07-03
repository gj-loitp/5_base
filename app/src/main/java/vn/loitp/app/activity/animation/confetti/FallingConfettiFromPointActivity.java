package vn.loitp.app.activity.animation.confetti;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.animation.confetti.CommonConfetti;
import vn.loitp.views.animation.confetti.ConfettiManager;
import vn.loitp.views.animation.confetti.ConfettiSource;

//https://github.com/jinatonic/confetti
public class FallingConfettiFromPointActivity extends BaseFontActivity implements View.OnClickListener {
    protected int goldDark, goldMed, gold, goldLight;
    protected int[] colors;

    private final List<ConfettiManager> activeConfettiManagers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.generate_confetti_once_btn).setOnClickListener(this);
        findViewById(R.id.generate_confetti_stream_btn).setOnClickListener(this);
        findViewById(R.id.generate_confetti_infinite_btn).setOnClickListener(this);
        findViewById(R.id.generate_confetti_stop_btn).setOnClickListener(this);

        final Resources res = getResources();
        goldDark = res.getColor(R.color.gold_dark);
        goldMed = res.getColor(R.color.gold_med);
        gold = res.getColor(R.color.gold);
        goldLight = res.getColor(R.color.gold_light);
        colors = new int[]{goldDark, goldMed, gold, goldLight};
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
        return R.layout.activity_falling_confetti_from_top;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.generate_confetti_once_btn) {
            activeConfettiManagers.add(generateOnce());
        } else if (id == R.id.generate_confetti_stream_btn) {
            activeConfettiManagers.add(generateStream());
        } else if (id == R.id.generate_confetti_infinite_btn) {
            activeConfettiManagers.add(generateInfinite());
        } else {
            for (ConfettiManager confettiManager : activeConfettiManagers) {
                confettiManager.terminate();
            }
            activeConfettiManagers.clear();
        }
    }

    protected ConfettiManager generateOnce() {
        return getCommonConfetti().oneShot();
    }

    protected ConfettiManager generateStream() {
        return getCommonConfetti().stream(3000);
    }

    protected ConfettiManager generateInfinite() {
        return getCommonConfetti().infinite();
    }

    private CommonConfetti getCommonConfetti() {
        final int size = getResources().getDimensionPixelSize(R.dimen.default_confetti_size);
        final ConfettiSource confettiSource = new ConfettiSource(-size, -size);
        final CommonConfetti commonConfetti =
                CommonConfetti.rainingConfetti(getRootView(), confettiSource, colors);

        final Resources res = getResources();
        final int velocitySlow = res.getDimensionPixelOffset(R.dimen.default_velocity_slow);
        final int velocityNormal = res.getDimensionPixelOffset(R.dimen.default_velocity_normal);
        final int velocityFast = res.getDimensionPixelOffset(R.dimen.default_velocity_fast);

        // Further configure it
        commonConfetti.getConfettiManager()
                .setVelocityX(velocityFast, velocityNormal)
                .setAccelerationX(-velocityNormal, velocitySlow)
                .setTargetVelocityX(0, velocitySlow / 2)
                .setVelocityY(velocityNormal, velocitySlow);

        return commonConfetti;
    }
}
