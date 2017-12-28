package vn.loitp.views.animation.flyschool;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

import loitp.core.R;

/**
 * ShapeView to be used inside {@link ShapeFlyer}
 * for Android >= LOLLIPOP
 * Created by avin on 31/01/17.
 */

public class ShapeView extends ImageView implements ShapeSetter {
    public ShapeView(Context context) {
        super(context);
        init();
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setImageResource(R.drawable.facebook_button_blue);
        //int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());
    }

    //private int dimensionInDp;

    @Override
    public void setShape(int drawable) {
        setImageResource(drawable);
        Utils.setHeart(this);
    }

    @Override
    public void setShape(ImgObject imgObject, int drawableRes) {
        //setImageResource(R.mipmap.ic_launcher);
    }
}
