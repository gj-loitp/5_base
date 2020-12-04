package com.views.sticker;

/**
 * Created by www.muathu@gmail.com on 10/21/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
//TODO convert kotlin
public class StickerTextView extends StickerView {
    private AutoResizeTextView tvMain;

    public StickerTextView(Context context) {
        super(context);
    }

    public StickerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickerTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public View getMainView() {
        if (tvMain != null)
            return tvMain;

        tvMain = new AutoResizeTextView(getContext());
        //tv_main.setTextSize(22);
        tvMain.setTextColor(Color.WHITE);
        tvMain.setGravity(Gravity.CENTER);
        tvMain.setTextSize(400);
        tvMain.setShadowLayer(4, 0, 0, Color.BLACK);
        tvMain.setMaxLines(1);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.CENTER;
        tvMain.setLayoutParams(params);
        if (getImageViewFlip() != null)
            getImageViewFlip().setVisibility(View.GONE);
        return tvMain;
    }

    public void setText(String text) {
        if (tvMain != null)
            tvMain.setText(text);
    }

    public String getText() {
        if (tvMain != null)
            return tvMain.getText().toString();

        return null;
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    @Override
    protected void onScaling(boolean scaleUp) {
        super.onScaling(scaleUp);
    }
}
