package vn.loitp.app.activity.customviews.layout.swipeablelayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.layout.swipeablelayout.frame.SwipeableLayout;
import vn.loitp.views.layout.swipeablelayout.listener.LayoutShiftListener;
import vn.loitp.views.layout.swipeablelayout.listener.OnLayoutPercentageChangeListener;
import vn.loitp.views.layout.swipeablelayout.listener.OnLayoutSwipedListener;

//read more
//https://github.com/ReginFell/SwipeableLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2729

public class SwipeableLayoutActivity extends BaseActivity {
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeableLayout swipeableLayout = (SwipeableLayout) findViewById(R.id.swipeableLayout);
        bt = (Button) findViewById(R.id.bt);

        swipeableLayout.setOnLayoutPercentageChangeListener(new OnLayoutPercentageChangeListener() {
            //OnLayoutPercentageChange return value from 0 to 1,
            //where 0 - view not swiped, 1 - view swiped to action

            @Override
            public void percentageY(float percentage) {
                //LLog.d(TAG, "percentageY " + percentage);
            }

            @Override
            public void percentageX(float percentage) {
                //LLog.d(TAG, "percentageY " + percentage);
            }
        });
        swipeableLayout.setOnSwipedListener(new OnLayoutSwipedListener() {
            @Override
            public void onLayoutSwiped() {
                //Do some action, when view was swiped. For example, you can close activity
                LLog.d(TAG, "setOnSwipedListener");
                LAnimationUtil.play(swipeableLayout, Techniques.FadeOut, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        swipeableLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onRepeat() {
                        //do nothing
                    }

                    @Override
                    public void onStart() {
                        //do nothing
                    }
                });
            }
        });
        swipeableLayout.setLayoutShiftListener(new LayoutShiftListener() {
            @Override
            public void onLayoutShifted(float positionX, float positionY, boolean isTouched) {
                //LLog.d(TAG, "onLayoutShifted " + positionX + "x" + positionY + " ,isTouched: " + isTouched);
            }
        });
        swipeableLayout.setSwipeSpeed(1);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeableLayout.setVisibility(View.VISIBLE);
                LAnimationUtil.play(swipeableLayout, Techniques.FadeIn);
            }
        });
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
        return R.layout.activity_swipeable_layout;
    }
}
