package vn.loitp.app.activity.customviews.imageview.scrollparallaximageview;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.core.base.BaseFontActivity;
import com.views.imageview.scrollparallaximageview.ScrollParallaxImageView;
import com.views.imageview.scrollparallaximageview.parallaxstyle.HorizontalScaleStyle;
import com.views.imageview.scrollparallaximageview.parallaxstyle.VerticalMovingStyle;

import loitp.basemaster.R;

//https://github.com/gjiazhe/ScrollParallaxImageView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=4759
public class ScrollParallaxImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollParallaxImageView iv = (ScrollParallaxImageView) findViewById(R.id.img);
        iv.setParallaxStyles(new VerticalMovingStyle()); // or other parallax styles

        LinearLayout llHorizontal = (LinearLayout) findViewById(R.id.ll_horizontal);
        for (int i = 0; i < 10; i++) {
            ScrollParallaxImageView scrollParallaxImageView = new ScrollParallaxImageView(getActivity());
            scrollParallaxImageView.setImageResource(i % 2 == 0 ? R.drawable.iv : R.drawable.logo);
            scrollParallaxImageView.setParallaxStyles(new HorizontalScaleStyle()); // or other parallax styles
            llHorizontal.addView(scrollParallaxImageView);
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        for (int i = 0; i < 20; i++) {
            ScrollParallaxImageView scrollParallaxImageView = new ScrollParallaxImageView(getActivity());
            scrollParallaxImageView.setImageResource(i % 2 == 0 ? R.drawable.iv : R.drawable.logo);
            scrollParallaxImageView.setParallaxStyles(new VerticalMovingStyle()); // or other parallax styles
            ll.addView(scrollParallaxImageView);
        }
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_scrollparallax_imageview;
    }
}
