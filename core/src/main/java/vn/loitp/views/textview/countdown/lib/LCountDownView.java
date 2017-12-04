package vn.loitp.views.textview.countdown.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import loitp.core.R;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LLog;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LCountDownView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private TextView tvCountDown;

    public LCountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LCountDownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_count_down, this);
        this.tvCountDown = (TextView) findViewById(R.id.tv_count_down);
    }

    public void start(int number) {
        this.number = number;
        doPerSec();
    }

    public void setShowOrHide(boolean isShow) {
        if (tvCountDown != null) {
            tvCountDown.setVisibility(isShow ? VISIBLE : INVISIBLE);
        }
    }

    private int number;

    public interface Callback {
        public void onTick();

        public void onEnd();
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void doPerSec() {
        if (tvCountDown != null) {
            tvCountDown.setText(number + "");
            LAnimationUtil.playDuration(tvCountDown, Techniques.FlipInX, 1000, new LAnimationUtil.Callback() {
                @Override
                public void onCancel() {
                    //do nothing
                }

                @Override
                public void onEnd() {
                    number--;
                    //LLog.d(TAG, "number " + number);
                    if (number == 0) {
                        tvCountDown.setText("GO");
                        LAnimationUtil.play(tvCountDown, Techniques.Flash, new LAnimationUtil.Callback() {
                            @Override
                            public void onCancel() {
                                //do nothing
                            }

                            @Override
                            public void onEnd() {
                                tvCountDown.setVisibility(GONE);
                                //tvCountDown = null;
                                LLog.d(TAG, "number == 0 -> STOP");
                                if (callback != null) {
                                    callback.onEnd();
                                }
                            }

                            @Override
                            public void onRepeat() {
                                //do nothing
                            }

                            @Override
                            public void onStart() {
                                //do nothing
                            }
                        });
                        return;
                    }
                    doPerSec();
                }

                @Override
                public void onRepeat() {
                    //do nothing
                }

                @Override
                public void onStart() {
                    //do nothing
                    if (callback != null) {
                        callback.onTick();
                    }
                }
            });
        }
    }
}