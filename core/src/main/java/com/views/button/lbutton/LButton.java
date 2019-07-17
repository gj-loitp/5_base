package com.views.button.lbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

import loitp.core.R;

/**
 * Created by LENOVO on 7/5/2018.
 */

public class LButton extends ImageButton {
    private final String TAG = getClass().getSimpleName();

    public LButton(Context context) {
        super(context);
    }

    public LButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int maskedAction = event.getActionMasked();
        if (maskedAction == MotionEvent.ACTION_DOWN) {
            this.setBackgroundResource(pressedDrawable);
        } else if (maskedAction == MotionEvent.ACTION_UP) {
            this.setBackgroundResource(0);
        }
        return super.onTouchEvent(event);
    }

    private int pressedDrawable = R.drawable.circle_effect;

    public void setPressedDrawable(int pressedDrawable) {
        this.pressedDrawable = pressedDrawable;
    }
}
