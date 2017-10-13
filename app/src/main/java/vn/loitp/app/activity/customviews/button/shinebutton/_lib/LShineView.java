package vn.loitp.app.activity.customviews.button.shinebutton._lib;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;

import vn.loitp.app.utilities.LAnimationUtil;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LShineView extends RelativeLayout {
    //guide: https://github.com/ChadCSong/ShineButton

    private final String TAG = getClass().getSimpleName();
    private ImageView iv;
    private ShineButton shineButton;

    public LShineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LShineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_shine, this);

        this.iv = (ImageView) findViewById(R.id.iv);
        this.shineButton = (ShineButton) findViewById(R.id.shine_button);
        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shineButton.performClick();
                LAnimationUtil.play(iv, Techniques.Pulse);
                if (callback != null) {
                    if (isDelay) {
                        LUIUtil.setDelay(750, new LUIUtil.DelayCallback() {
                            @Override
                            public void doAfter(int mls) {
                                callback.onClick(v);
                            }
                        });
                    } else {
                        callback.onClick(v);
                    }
                }
            }
        });
    }

    public void setSize(int sizeImageViewInDP, int sizeShineButtonInDP) {
        int dimensionInDpImageView = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeImageViewInDP, getResources().getDisplayMetrics());
        iv.getLayoutParams().height = dimensionInDpImageView;
        iv.getLayoutParams().width = dimensionInDpImageView;
        iv.requestLayout();

        int dimensionInDpShineButton = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeShineButtonInDP, getResources().getDisplayMetrics());
        shineButton.getLayoutParams().height = dimensionInDpShineButton;
        shineButton.getLayoutParams().width = dimensionInDpShineButton;
        shineButton.requestLayout();
    }

    public interface Callback {
        public void onClick(View view);
    }

    private boolean isDelay = true;

    public void setDelay(boolean isDelay) {
        this.isDelay = isDelay;
    }

    private Callback callback;

    public void setOnClick(Callback callback) {
        this.callback = callback;
    }

    public void setImage(int resID) {
        iv.setImageResource(resID);
    }

    public void setEnabledIv(boolean isEnable) {
        iv.setEnabled(isEnable);
    }

    public void setPerformClick() {
        iv.performClick();
    }
}