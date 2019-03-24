package vn.loitp.views.navigationview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LTextNavigationView extends RelativeLayout implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private TextView tvPrev;
    private TextView tvNext;
    private TextView tv;
    private List<String> stringList;
    private int currenIndex;
    private boolean isEnableAnimation = true;
    private int colorOn = Color.BLACK;
    private int colorOff = Color.GRAY;
    private NVCallback nvCallback;

    public interface NVCallback {
        public void onIndexChange(int index, String s);
    }

    public void setNVCallback(NVCallback nvCallback) {
        this.nvCallback = nvCallback;
    }

    public LTextNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LTextNavigationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_navigation_text, this);
        this.tv = (TextView) findViewById(R.id.tv);
        this.tvPrev = (TextView) findViewById(R.id.tv_prev);
        this.tvNext = (TextView) findViewById(R.id.tv_next);
        tvPrev.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        setEnableController(tvPrev, false);
        setEnableController(tvNext, false);
    }

    private void setEnableController(TextView textView, boolean isEnable) {
        if (isEnable) {
            textView.setEnabled(true);
            textView.setClickable(true);
            textView.setTextColor(colorOn);
        } else {
            textView.setEnabled(false);
            textView.setClickable(false);
            textView.setTextColor(colorOff);
        }
    }

    public void setStringList(List<String> stringList) {
        if (stringList == null || stringList.isEmpty()) {
            return;
        }
        currenIndex = 0;
        this.stringList = stringList;
        updateUI();
    }

    public void setCurrenIndex(int index) {
        if (stringList == null || stringList.isEmpty()) {
            return;
        }
        if (index < 0 || index > stringList.size() - 1) {
            return;
        }
        currenIndex = index;
        updateUI();
    }

    private void updateUI() {
        String s = stringList.get(currenIndex);
        tv.setText(s);
        if (isEnableAnimation) {
            LAnimationUtil.play(tv, Techniques.FlipInX);
        }
        int size = stringList.size();
        if (size == 1) {
            setEnableController(tvPrev, false);
            setEnableController(tvNext, false);
        } else {
            if (currenIndex <= 0) {
                setEnableController(tvPrev, false);
                setEnableController(tvNext, true);
            } else if (currenIndex >= size - 1) {
                setEnableController(tvPrev, true);
                setEnableController(tvNext, false);
            } else {
                setEnableController(tvPrev, true);
                setEnableController(tvNext, true);
            }
        }
        if (nvCallback != null) {
            nvCallback.onIndexChange(currenIndex, s);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == tvPrev) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view, Techniques.Pulse);
            }
            currenIndex--;
            updateUI();
        } else if (view == tvNext) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view, Techniques.Pulse);
            }
            currenIndex++;
            updateUI();
        }
    }

    public TextView getTvPrev() {
        return tvPrev;
    }

    public TextView getTvNext() {
        return tvNext;
    }

    public TextView getTv() {
        return tv;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public int getCurrenIndex() {
        return currenIndex;
    }

    public int getColorOn() {
        return colorOn;
    }

    public void setColorOn(int colorOn) {
        this.colorOn = colorOn;
    }

    public int getColorOff() {
        return colorOff;
    }

    public void setColorOff(int colorOff) {
        this.colorOff = colorOff;
    }

    public boolean isEnableAnimation() {
        return isEnableAnimation;
    }

    public void setEnableAnimation(boolean enableAnimation) {
        isEnableAnimation = enableAnimation;
    }

    public void setTextPrev(String prev) {
        tvPrev.setText(prev);
    }

    public void setTextNext(String next) {
        tvPrev.setText(next);
    }

    public void setTextSize(int dpPrev, int dpText, int dpNext) {
        LUIUtil.setTextSize(tvPrev, TypedValue.COMPLEX_UNIT_DIP, dpPrev);
        LUIUtil.setTextSize(tv, TypedValue.COMPLEX_UNIT_DIP, dpText);
        LUIUtil.setTextSize(tvNext, TypedValue.COMPLEX_UNIT_DIP, dpNext);
    }
}