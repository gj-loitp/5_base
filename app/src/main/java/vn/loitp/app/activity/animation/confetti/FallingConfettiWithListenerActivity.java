package vn.loitp.app.activity.animation.confetti;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.animation.confetti.CommonConfetti;
import vn.loitp.views.animation.confetti.ConfettiManager;
import vn.loitp.views.animation.confetti.ConfettiSource;
import vn.loitp.views.animation.confetti.ConfettoGenerator;
import vn.loitp.views.animation.confetti.confetto.BitmapConfetto;
import vn.loitp.views.animation.confetti.confetto.Confetto;

//https://github.com/jinatonic/confetti
public class FallingConfettiWithListenerActivity extends BaseFontActivity implements View.OnClickListener, ConfettoGenerator, ConfettiManager.ConfettiAnimationListener {
    protected int goldDark, goldMed, gold, goldLight;
    protected int[] colors;

    private final List<ConfettiManager> activeConfettiManagers = new ArrayList<>();
    private TextView numConfettiTxt;
    private int numConfettiOnScreen;

    private int size;
    private int velocitySlow, velocityNormal;
    private Bitmap bitmap;

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

        numConfettiTxt = (TextView) findViewById(R.id.num_confetti_txt);
        numConfettiTxt.setVisibility(View.VISIBLE);

        size = res.getDimensionPixelSize(R.dimen.big_confetti_size);
        velocitySlow = res.getDimensionPixelOffset(R.dimen.default_velocity_slow);
        velocityNormal = res.getDimensionPixelOffset(R.dimen.default_velocity_normal);

        bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.heart),
                size, size, false);
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

    private CommonConfetti getCommonConfetti() {
        final int centerX = getRootView().getWidth() / 2;
        final int centerY = getRootView().getHeight() / 5 * 2;
        return CommonConfetti.explosion(getRootView(), centerX, centerY, colors);
    }

    protected ConfettiManager generateOnce() {
        return getConfettiManager().setNumInitialCount(20)
                .setEmissionDuration(0)
                .setConfettiAnimationListener(this)
                .animate();
    }

    protected ConfettiManager generateStream() {
        return getConfettiManager().setNumInitialCount(0)
                .setEmissionDuration(3000)
                .setEmissionRate(20)
                .setConfettiAnimationListener(this)
                .animate();
    }

    protected ConfettiManager generateInfinite() {
        return getConfettiManager().setNumInitialCount(0)
                .setEmissionDuration(ConfettiManager.INFINITE_DURATION)
                .setEmissionRate(20)
                .setConfettiAnimationListener(this)
                .animate();
    }

    private ConfettiManager getConfettiManager() {
        final ConfettiSource source = new ConfettiSource(0, -size, getRootView().getWidth(), -size);
        return new ConfettiManager(this, this, source, getRootView())
                .setVelocityX(0, velocitySlow)
                .setVelocityY(velocityNormal, velocitySlow)
                .setRotationalVelocity(180, 90)
                .setTouchEnabled(true);
    }

    @Override
    public Confetto generateConfetto(Random random) {
        return new BitmapConfetto(bitmap);
    }

    @Override
    public void onAnimationStart(ConfettiManager confettiManager) {
        Toast.makeText(this, "Starting confetti animation", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(ConfettiManager confettiManager) {
        numConfettiOnScreen = 0;
        updateNumConfettiTxt();
        Toast.makeText(this, "Ending confetti animation", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfettoEnter(Confetto confetto) {
        numConfettiOnScreen++;
        updateNumConfettiTxt();
    }

    @Override
    public void onConfettoExit(Confetto confetto) {
        numConfettiOnScreen--;
        updateNumConfettiTxt();
    }

    private void updateNumConfettiTxt() {
        numConfettiTxt.setText(getString(R.string.num_confetti_desc, numConfettiOnScreen));
    }
}
