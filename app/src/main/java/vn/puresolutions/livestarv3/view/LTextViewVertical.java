package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.puresolutions.livestar.R;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LTextViewVertical extends RelativeLayout implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private ImageView ivIcon;
    private TextView tvDescription;
    private LinearLayout llMain;

    public LTextViewVertical(Context context) {
        super(context);
        init();
    }

    public LTextViewVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LTextViewVertical(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_text_view_vertical, this);

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

    public void setTintImage(int color) {
        ivIcon.setColorFilter(color);
    }

    public void setText(String text) {
        tvDescription.setText(text);
    }

    /*public void setSizeIconImageView(boolean isSmall) {
        int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, isSmall ? 30 : 45, getResources().getDisplayMetrics());
        ivIcon.getLayoutParams().height = dimensionInDp;
        ivIcon.getLayoutParams().width = dimensionInDp;
        ivIcon.requestLayout();
    }*/
}