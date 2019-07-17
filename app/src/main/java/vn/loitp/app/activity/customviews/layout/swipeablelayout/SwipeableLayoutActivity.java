package vn.loitp.app.activity.customviews.layout.swipeablelayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.core.base.BaseFontActivity;
import com.core.utilities.LAnimationUtil;
import com.core.utilities.LLog;
import com.daimajia.androidanimations.library.Techniques;
import com.views.layout.swipeablelayout.frame.SwipeableLayout;
import com.views.layout.swipeablelayout.listener.LayoutShiftListener;
import com.views.layout.swipeablelayout.listener.OnLayoutPercentageChangeListener;
import com.views.layout.swipeablelayout.listener.OnLayoutSwipedListener;

import loitp.basemaster.R;

//read more
//https://github.com/ReginFell/SwipeableLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2729

public class SwipeableLayoutActivity extends BaseFontActivity {
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
                LLog.INSTANCE.d(getTAG(), "setOnSwipedListener");
                LAnimationUtil.INSTANCE.play(swipeableLayout, Techniques.FadeOut, new LAnimationUtil.Callback() {
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
                LAnimationUtil.INSTANCE.play(swipeableLayout, Techniques.FadeIn);
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
