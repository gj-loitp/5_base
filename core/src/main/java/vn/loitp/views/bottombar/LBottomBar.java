package vn.loitp.views.bottombar;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import loitp.core.R;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.views.realtimeblurview.RealtimeBlurView;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LBottomBar extends RelativeLayout implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private RealtimeBlurView blurView;
    private LinearLayout llIcon0;
    private LinearLayout llIcon1;
    private LinearLayout llIcon2;
    private LinearLayout llIcon3;
    private LinearLayout llIcon4;
    private LinearLayout llIcon5;
    private ImageView ivIcon0;
    private ImageView ivIcon1;
    private ImageView ivIcon2;
    private ImageView ivIcon3;
    private ImageView ivIcon4;
    private ImageView ivIcon5;
    private TextView tvIcon0;
    private TextView tvIcon1;
    private TextView tvIcon2;
    private TextView tvIcon3;
    private TextView tvIcon4;
    private TextView tvIcon5;
    private int previousPos;
    private int currentPos;
    public final static int PAGE_NONE = -1;
    public final static int PAGE_0 = 0;
    public final static int PAGE_1 = 1;
    public final static int PAGE_2 = 2;
    public final static int PAGE_3 = 3;
    public final static int PAGE_4 = 4;
    public final static int PAGE_5 = 5;

    public LBottomBar(Context context) {
        super(context);
        init();
    }

    public LBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LBottomBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RealtimeBlurView getBlurView() {
        return blurView;
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_bottom_bar, this);
        this.blurView = (RealtimeBlurView) findViewById(R.id.real_time_blur_view);
        this.llIcon0 = (LinearLayout) findViewById(R.id.ll_icon_0);
        this.llIcon1 = (LinearLayout) findViewById(R.id.ll_icon_1);
        this.llIcon2 = (LinearLayout) findViewById(R.id.ll_icon_2);
        this.llIcon3 = (LinearLayout) findViewById(R.id.ll_icon_3);
        this.llIcon4 = (LinearLayout) findViewById(R.id.ll_icon_4);
        this.llIcon5 = (LinearLayout) findViewById(R.id.ll_icon_5);
        this.ivIcon0 = (ImageView) findViewById(R.id.iv_icon_0);
        this.ivIcon1 = (ImageView) findViewById(R.id.iv_icon_1);
        this.ivIcon2 = (ImageView) findViewById(R.id.iv_icon_2);
        this.ivIcon3 = (ImageView) findViewById(R.id.iv_icon_3);
        this.ivIcon4 = (ImageView) findViewById(R.id.iv_icon_4);
        this.ivIcon5 = (ImageView) findViewById(R.id.iv_icon_5);
        this.tvIcon0 = (TextView) findViewById(R.id.tv_icon_0);
        this.tvIcon1 = (TextView) findViewById(R.id.tv_icon_1);
        this.tvIcon2 = (TextView) findViewById(R.id.tv_icon_2);
        this.tvIcon3 = (TextView) findViewById(R.id.tv_icon_3);
        this.tvIcon4 = (TextView) findViewById(R.id.tv_icon_4);
        this.tvIcon5 = (TextView) findViewById(R.id.tv_icon_5);
        llIcon0.setOnClickListener(this);
        llIcon1.setOnClickListener(this);
        llIcon2.setOnClickListener(this);
        llIcon3.setOnClickListener(this);
        llIcon4.setOnClickListener(this);
        llIcon5.setOnClickListener(this);
        updateView(ivIcon0, tvIcon0);
    }

    public void setCount(int count) {
        if (count < 0 || count >= 7) {
            throw new IllegalArgumentException("Error value of count number, value must between 0 and 7.");
        }
        if (count == 0) {
            llIcon0.setVisibility(View.GONE);
            llIcon1.setVisibility(View.GONE);
            llIcon2.setVisibility(View.GONE);
            llIcon3.setVisibility(View.GONE);
            llIcon4.setVisibility(View.GONE);
            llIcon5.setVisibility(View.GONE);
        } else if (count == 1) {
            llIcon0.setVisibility(View.VISIBLE);
            llIcon1.setVisibility(View.GONE);
            llIcon2.setVisibility(View.GONE);
            llIcon3.setVisibility(View.GONE);
            llIcon4.setVisibility(View.GONE);
            llIcon5.setVisibility(View.GONE);
        } else if (count == 2) {
            llIcon0.setVisibility(View.VISIBLE);
            llIcon1.setVisibility(View.VISIBLE);
            llIcon2.setVisibility(View.GONE);
            llIcon3.setVisibility(View.GONE);
            llIcon4.setVisibility(View.GONE);
            llIcon5.setVisibility(View.GONE);
        } else if (count == 3) {
            llIcon0.setVisibility(View.VISIBLE);
            llIcon1.setVisibility(View.VISIBLE);
            llIcon2.setVisibility(View.VISIBLE);
            llIcon3.setVisibility(View.GONE);
            llIcon4.setVisibility(View.GONE);
            llIcon5.setVisibility(View.GONE);
        } else if (count == 4) {
            llIcon0.setVisibility(View.VISIBLE);
            llIcon1.setVisibility(View.VISIBLE);
            llIcon2.setVisibility(View.VISIBLE);
            llIcon3.setVisibility(View.VISIBLE);
            llIcon4.setVisibility(View.GONE);
            llIcon5.setVisibility(View.GONE);
        } else if (count == 5) {
            llIcon0.setVisibility(View.VISIBLE);
            llIcon1.setVisibility(View.VISIBLE);
            llIcon2.setVisibility(View.VISIBLE);
            llIcon3.setVisibility(View.VISIBLE);
            llIcon4.setVisibility(View.VISIBLE);
            llIcon5.setVisibility(View.GONE);
        } else if (count == 6) {
            llIcon0.setVisibility(View.VISIBLE);
            llIcon1.setVisibility(View.VISIBLE);
            llIcon2.setVisibility(View.VISIBLE);
            llIcon3.setVisibility(View.VISIBLE);
            llIcon4.setVisibility(View.VISIBLE);
            llIcon5.setVisibility(View.VISIBLE);
        }
    }

    public void setItem0(int resImg, String name) {
        ivIcon0.setImageResource(resImg);
        tvIcon0.setText(name);
    }

    public void setItem1(int resImg, String name) {
        ivIcon1.setImageResource(resImg);
        tvIcon1.setText(name);
    }

    public void setItem2(int resImg, String name) {
        ivIcon2.setImageResource(resImg);
        tvIcon2.setText(name);
    }

    public void setItem3(int resImg, String name) {
        ivIcon3.setImageResource(resImg);
        tvIcon3.setText(name);
    }

    public void setItem4(int resImg, String name) {
        ivIcon4.setImageResource(resImg);
        tvIcon4.setText(name);
    }

    public void setItem5(int resImg, String name) {
        ivIcon5.setImageResource(resImg);
        tvIcon5.setText(name);
    }

    @Override
    public void onClick(View v) {
        if (v == llIcon0) {
            if (currentPos != PAGE_0) {
                previousPos = currentPos;
                currentPos = PAGE_0;
                onClickItem(currentPos);
                updateView(ivIcon0, tvIcon0);
            }
        } else if (v == llIcon1) {
            if (currentPos != PAGE_1) {
                previousPos = currentPos;
                currentPos = PAGE_1;
                onClickItem(currentPos);
                updateView(ivIcon1, tvIcon1);
            }
        } else if (v == llIcon2) {
            if (currentPos != PAGE_2) {
                previousPos = currentPos;
                currentPos = PAGE_2;
                onClickItem(currentPos);
                updateView(ivIcon2, tvIcon2);
            }
        } else if (v == llIcon3) {
            if (currentPos != PAGE_3) {
                previousPos = currentPos;
                currentPos = PAGE_3;
                onClickItem(currentPos);
                updateView(ivIcon3, tvIcon3);
            }
        } else if (v == llIcon4) {
            if (currentPos != PAGE_4) {
                previousPos = currentPos;
                currentPos = PAGE_4;
                onClickItem(currentPos);
                updateView(ivIcon4, tvIcon4);
            }
        } else if (v == llIcon5) {
            if (currentPos != PAGE_5) {
                previousPos = currentPos;
                currentPos = PAGE_5;
                onClickItem(currentPos);
                updateView(ivIcon5, tvIcon5);
            }
        }
    }

    private int colorIvOn = R.color.colorPrimary;
    private int colorIvOff = R.color.Black;
    private Techniques techniques = Techniques.Pulse;

    public void setTechniques(Techniques techniques) {
        this.techniques = techniques;
    }

    public int getColorIvOn() {
        return colorIvOn;
    }

    public void setColorIvOn(int colorOn) {
        this.colorIvOn = colorOn;
    }

    public int getColorIvOff() {
        return colorIvOff;
    }

    public void setColorIvOff(int colorOff) {
        this.colorIvOff = colorOff;
    }

    public void setColorTextView(int colorRes) {
        tvIcon0.setTextColor(ContextCompat.getColor(getContext(), colorRes));
        tvIcon1.setTextColor(ContextCompat.getColor(getContext(), colorRes));
        tvIcon2.setTextColor(ContextCompat.getColor(getContext(), colorRes));
        tvIcon3.setTextColor(ContextCompat.getColor(getContext(), colorRes));
        tvIcon4.setTextColor(ContextCompat.getColor(getContext(), colorRes));
        tvIcon5.setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    private void updateView(ImageView imageView, TextView textView) {
        if (techniques != null) {
            LAnimationUtil.play(imageView, techniques);
        }
        ivIcon0.setColorFilter(ContextCompat.getColor(getContext(), colorIvOff));
        ivIcon1.setColorFilter(ContextCompat.getColor(getContext(), colorIvOff));
        ivIcon2.setColorFilter(ContextCompat.getColor(getContext(), colorIvOff));
        ivIcon3.setColorFilter(ContextCompat.getColor(getContext(), colorIvOff));
        ivIcon4.setColorFilter(ContextCompat.getColor(getContext(), colorIvOff));
        ivIcon5.setColorFilter(ContextCompat.getColor(getContext(), colorIvOff));
        tvIcon0.setVisibility(View.GONE);
        tvIcon1.setVisibility(View.GONE);
        tvIcon2.setVisibility(View.GONE);
        tvIcon3.setVisibility(View.GONE);
        tvIcon4.setVisibility(View.GONE);
        tvIcon5.setVisibility(View.GONE);
        ivIcon0.setPadding(15, 15, 15, 15);
        ivIcon1.setPadding(15, 15, 15, 15);
        ivIcon2.setPadding(15, 15, 15, 15);
        ivIcon3.setPadding(15, 15, 15, 15);
        ivIcon4.setPadding(15, 15, 15, 15);
        ivIcon5.setPadding(15, 15, 15, 15);
        imageView.setColorFilter(ContextCompat.getColor(getContext(), colorIvOn));
        imageView.setPadding(5, 5, 5, 5);
        textView.setVisibility(View.VISIBLE);
    }

    private void onClickItem(int pos) {
        if (callback != null) {
            callback.OnClickItem(pos);
        }
    }

    private Callback callback;

    public interface Callback {
        public void OnClickItem(int position);
    }

    public void setOnItemClick(Callback callback) {
        this.callback = callback;
    }

    public void setPerformItemClick(int position) {
        //LLog.d(TAG, "setPerformItemClick " + position);
        previousPos = currentPos;
        currentPos = position;
        switch (position) {
            case PAGE_0:
                onClickItem(PAGE_0);
                updateView(ivIcon0, tvIcon0);
                break;
            case PAGE_1:
                onClickItem(PAGE_1);
                updateView(ivIcon1, tvIcon1);
                break;
            case PAGE_2:
                onClickItem(PAGE_2);
                updateView(ivIcon2, tvIcon2);
                break;
            case PAGE_3:
                onClickItem(PAGE_3);
                updateView(ivIcon3, tvIcon3);
                break;
            case PAGE_4:
                onClickItem(PAGE_4);
                updateView(ivIcon4, tvIcon4);
                break;
            case PAGE_5:
                onClickItem(PAGE_5);
                updateView(ivIcon5, tvIcon5);
                break;
        }
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public int getPreviousPos() {
        return previousPos;
    }
}