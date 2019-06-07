package vn.loitp.views.autosize.imagebuttonwithsize;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.ImageButton;

import vn.loitp.core.utilities.LLog;
import vn.loitp.utils.util.ConvertUtils;
import vn.loitp.utils.util.ScreenUtils;

/**
 * Created by LENOVO on 4/19/2018.
 */

public class ImageButtonWithSize extends ImageButton {
    private final String TAG = getClass().getSimpleName();

    public ImageButtonWithSize(Context context) {
        super(context);
    }

    public ImageButtonWithSize(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageButtonWithSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageButtonWithSize(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private final int defaultSizePortrait = 50;
    private final int defaultSizeLandscape = 150;
    private int portraitSizeW = defaultSizePortrait;
    private int portraitSizeH = defaultSizePortrait;
    private int landscapeSizeW = defaultSizeLandscape;
    private int landscapeSizeH = defaultSizeLandscape;

    public void setPortraitSizeWInPx(int portraitSizeW) {
        this.portraitSizeW = portraitSizeW;
        updateSize();
    }

    public void setPortraitSizeHInPx(int portraitSizeH) {
        this.portraitSizeH = portraitSizeH;
        updateSize();
    }

    public void setLandscapeSizeWInPx(int landscapeSizeW) {
        this.landscapeSizeW = landscapeSizeW;
        updateSize();
    }

    public void setLandscapeSizeHInPx(int landscapeSizeH) {
        this.landscapeSizeH = landscapeSizeH;
        updateSize();
    }

    public void setPortraitSizeWInDp(float portraitSizeW) {
        this.portraitSizeW = ConvertUtils.dp2px(portraitSizeW);
        updateSize();
    }

    public void setPortraitSizeHInDp(float portraitSizeH) {
        this.portraitSizeH = ConvertUtils.dp2px(portraitSizeH);
        updateSize();
    }

    public void setLandscapeSizeWInDp(float landscapeSizeW) {
        this.landscapeSizeW = ConvertUtils.dp2px(landscapeSizeW);
        updateSize();
    }

    public void setLandscapeSizeHInDp(float landscapeSizeH) {
        this.landscapeSizeH = ConvertUtils.dp2px(landscapeSizeH);
        updateSize();
    }

    private void updateSize() {
        boolean isPortrait = ScreenUtils.isPortrait();
        LLog.INSTANCE.d(TAG, "updateSize isPortrait " + isPortrait);
        LLog.INSTANCE.d(TAG, "size portrait: " + portraitSizeW + "x" + portraitSizeH);
        LLog.INSTANCE.d(TAG, "size landscape: " + landscapeSizeW + "x" + landscapeSizeH);
        if (isPortrait) {
            this.getLayoutParams().width = portraitSizeW;
            this.getLayoutParams().height = portraitSizeH;
        } else {
            this.getLayoutParams().width = landscapeSizeW;
            this.getLayoutParams().height = landscapeSizeH;
        }
        invalidate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateSize();
    }
}
