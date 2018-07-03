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

//https://github.com/jinatonic/confetti
public class FallingConfettiFromTopActivity extends BaseFontActivity implements View.OnClickListener {
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
        return CommonConfetti.rainingConfetti(getRootView(), colors)
                .oneShot();
    }

    protected ConfettiManager generateStream() {
        return CommonConfetti.rainingConfetti(getRootView(), colors)
                .stream(3000);
    }

    protected ConfettiManager generateInfinite() {
        return CommonConfetti.rainingConfetti(getRootView(), colors)
                .infinite();
    }
}
