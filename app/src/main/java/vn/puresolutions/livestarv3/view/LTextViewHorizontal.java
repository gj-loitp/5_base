package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.puresolutions.livestar.R;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LTextViewHorizontal extends RelativeLayout implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private ImageView ivIcon;
    private TextView tvDescription;
    private LinearLayout llMain;

    public LTextViewHorizontal(Context context) {
        super(context);
        init();
    }

    public LTextViewHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LTextViewHorizontal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_text_view_horizontal, this);

        this.ivIcon = (ImageView) findViewById(R.id.iv_icon);
        this.tvDescription = (TextView) findViewById(R.id.tv_description);
        this.llMain = (LinearLayout) findViewById(R.id.ll_main);

        llMain.setOnClickListener(this);
    }

    public ImageView getIvIcon() {
        return ivIcon;
    }

    public TextView getTvDescription() {
        return tvDescription;
    }

    public void setBackground(int resBackground) {
        llMain.setBackgroundResource(resBackground);
    }

    @Override
    public void onClick(View v) {
        //LLog.d(TAG, "onClickRootView");
        switch (v.getId()) {
            case R.id.ll_main:
                if (callback != null) {
                    callback.OnClickItem();
                }
                break;
        }
    }

    private void onClickItem() {
        if (callback != null) {
            callback.OnClickItem();
        }
    }

    private Callback callback;

    public interface Callback {
        public void OnClickItem();
    }

    public void setOnItemClick(Callback callback) {
        this.callback = callback;
    }

    public void setImage(int resDrawable) {
        ivIcon.setImageResource(resDrawable);
    }

    public void setImageSize(int dp) {
        int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        ivIcon.getLayoutParams().height = dimensionInDp;
        ivIcon.getLayoutParams().width = dimensionInDp;
        ivIcon.requestLayout();
    }

    public void setImagePadding(int sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        ivIcon.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
    }

    public void setText(String text) {
        tvDescription.setText(text);
    }

    public void setTextSize(float size) {
        tvDescription.setTextSize(size);
    }

    /*public void setSizeIconImageView(boolean isSmall) {
        int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, isSmall ? 30 : 45, getResources().getDisplayMetrics());
        ivIcon.getLayoutParams().height = dimensionInDp;
        ivIcon.getLayoutParams().width = dimensionInDp;
        ivIcon.requestLayout();
    }*/
}