package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;
import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LViewPagerIndicator extends RelativeLayout {
    private LinearLayout ll;
    private int maxDot;

    //private int widthDotBigInDp = 0;
    //private int heightDotBigInDp = 0;
    //private final int dp15 = 15;
    private int marginPixel;

    private List<ImageView> dotList = new ArrayList<>();

    public LViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    public LViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LViewPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_view_pager_indicator, this);
        marginPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());//3dp

        //heightDotBigInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp15, getResources().getDisplayMetrics());//20 in dp
        //widthDotBigInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp15, getResources().getDisplayMetrics());//20 in dp

        this.ll = (LinearLayout) findViewById(R.id.ll);
    }

    public void setMaxDot(int newMaxDot) {
        if (maxDot == newMaxDot) {
            return;
        }
        if (ll != null) {
            ll.removeAllViews();
        }
        this.maxDot = newMaxDot;
        if (maxDot <= 0) {
            return;
        }

        for (int i = 0; i < maxDot; i++) {
            addDot();
        }
    }

    private void addDot() {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.circle_white);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(marginPixel, 0, marginPixel, 0);
        imageView.setLayoutParams(lp);

        ll.addView(imageView);

        if (!dotList.contains(imageView)) {
            dotList.add(imageView);
        }
    }

    public void setPosition(int position) {
        if (position < 0 || position > maxDot) {
            return;
        }
        for (int i = 0; i < dotList.size(); i++) {
            dotList.get(i).setImageResource(R.drawable.circle_gray);
        }
        dotList.get(position).setImageResource(R.drawable.circle_white);
        //LAnimationUtil.play(dotList.get(position), Techniques.FlipInX);
    }
}