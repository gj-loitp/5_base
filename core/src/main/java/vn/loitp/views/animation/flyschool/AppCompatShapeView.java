package vn.loitp.views.animation.flyschool;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import loitp.core.R;
import vn.loitp.core.utilities.LUIUtil;

/**
 * * ShapeView to be used inside {@link ShapeFlyer}
 * for Android < LOLLIPOP
 * <p>
 * Created by avin on 30/01/17.
 */

public class AppCompatShapeView extends AppCompatImageView implements ShapeSetter {
    public AppCompatShapeView(Context context) {
        super(context);
        init();
    }

    public AppCompatShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppCompatShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setImageResource(R.drawable.facebook_button_blue);
    }

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
