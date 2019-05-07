package vn.loitp.views.navigationview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.utilities.LAnimationUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LNavigationView extends RelativeLayout implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private ImageView ivPrev;
    private ImageView ivNext;
    private TextView tv;
    private List<String> stringList;
    private int currenIndex;
    private int colorOn = Color.BLACK;
    private int colorOff = Color.GRAY;
    private boolean isEnableAnimation = true;

    private NVCallback nvCallback;

    public interface NVCallback {
        public void onIndexChange(int index, String s);
    }

    public void setNVCallback(NVCallback nvCallback) {
        this.nvCallback = nvCallback;
    }

    public LNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LNavigationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_navigation, this);
        this.ivPrev = (ImageView) findViewById(R.id.iv_prev);
        this.ivNext = (ImageView) findViewById(R.id.iv_next);
        this.tv = (TextView) findViewById(R.id.tv);
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        setEnableController(ivPrev, false);
        setEnableController(ivNext, false);
    }

    private void setEnableController(ImageView imageView, boolean isEnable) {
        if (isEnable) {
            imageView.setEnabled(true);
            imageView.setClickable(true);
            imageView.setColorFilter(colorOn);
        } else {
            imageView.setEnabled(false);
            imageView.setClickable(false);
            imageView.setColorFilter(colorOff);
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
            setEnableController(ivPrev, false);
            setEnableController(ivNext, false);
        } else {
            if (currenIndex <= 0) {
                setEnableController(ivPrev, false);
                setEnableController(ivNext, true);
            } else if (currenIndex >= size - 1) {
                setEnableController(ivPrev, true);
                setEnableController(ivNext, false);
            } else {
                setEnableController(ivPrev, true);
                setEnableController(ivNext, true);
            }
        }
        if (nvCallback != null) {
            nvCallback.onIndexChange(currenIndex, s);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == ivPrev) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view, Techniques.Pulse);
            }
            currenIndex--;
            updateUI();
        } else if (view == ivNext) {
            if (isEnableAnimation) {
                LAnimationUtil.play(view, Techniques.Pulse);
            }
            currenIndex++;
            updateUI();
        }
    }

    public ImageView getIvPrev() {
        return ivPrev;
    }

    public ImageView getIvNext() {
        return ivNext;
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
}