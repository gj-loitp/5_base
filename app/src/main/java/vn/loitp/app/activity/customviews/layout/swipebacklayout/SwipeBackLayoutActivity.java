package vn.loitp.app.activity.customviews.layout.swipebacklayout;

import android.os.Bundle;
import android.widget.RadioGroup;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.layout.swipeback.SwipeBackLayout;

//https://github.com/gongwen/SwipeBackLayout
public class SwipeBackLayoutActivity extends BaseFontActivity {
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipeBackLayout);
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);
        mSwipeBackLayout.setMaskAlpha(125);
        mSwipeBackLayout.setSwipeBackFactor(0.5f);
        /*mSwipeBackLayout.setSwipeBackListener(new SwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void onViewPositionChanged(View mView, float swipeBackFraction, float SWIPE_BACK_FACTOR) {
                LLog.d(TAG, "onViewPositionChanged");
            }

            @Override
            public void onViewSwipeFinished(View mView, boolean isEnd) {
                LLog.d(TAG, "onViewSwipeFinished");
            }
        });*/

        RadioGroup rb = (RadioGroup) findViewById(R.id.radio_group);
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fromLeftRb:
                        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);
                        break;
                    case R.id.fromTopRb:
                        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_TOP);
                        break;
                    case R.id.fromRightRb:
                        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_RIGHT);
                        break;
                    case R.id.fromBottomRb:
                        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_BOTTOM);
                        break;
                }
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
        return R.layout.activity_swipeback_layout;
    }

    @Override
    protected void onDestroy() {
        LLog.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
