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
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;

/**
 * File created on 8/15/2017.
 *
 * @author anhdv
 */

public class LTextViewImageRight extends RelativeLayout implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private ImageView ivIcon;
    private TextView tvDescription;
    private LinearLayout llMain;
    public LTextViewImageRight(Context context) {
        super(context);
        init();
    }

    public LTextViewImageRight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LTextViewImageRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_text_view_image_right, this);
        this.ivIcon = (ImageView) findViewById(R.id.iv_icon);
        this.tvDescription = (TextView) findViewById(R.id.tv_description);
        this.llMain = (LinearLayout) findViewById(R.id.ll_main);

        ivIcon.setImageResource(R.drawable.next);
        llMain.setOnClickListener(this);
    }

    public void setBackground(int resBackground) {
        llMain.setBackgroundResource(resBackground);
    }

    @Override
    public void onClick(View v) {
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

    private LTextViewHorizontal.Callback callback;

    public interface Callback {
        public void OnClickItem();
    }

    public void setOnItemClick(LTextViewHorizontal.Callback callback) {
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

    public void setText(String text) {
        tvDescription.setText(text);
    }

    public void setTextAllCap(){
        tvDescription.setAllCaps(true);
    }
}
