package vn.loitp.app.activity.customviews.layout.swipebacklayout;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LScreenUtil;
import com.views.layout.swipeback.SwipeBackLayout;

import loitp.basemaster.R;

//https://github.com/gongwen/SwipeBackLayout
public class SwipeBackLayoutActivity extends BaseFontActivity {
    private SwipeBackLayout mSwipeBackLayout;
    private int screenW;
    private int screenH;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = findViewById(R.id.swipeBackLayout);
        view = findViewById(R.id.view);
        screenW = LScreenUtil.INSTANCE.getScreenWidth();
        screenH = LScreenUtil.INSTANCE.getScreenHeight();
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);
        mSwipeBackLayout.setMaskAlpha(125);
        mSwipeBackLayout.setSwipeBackFactor(0.5f);
        mSwipeBackLayout.setSwipeBackListener(new SwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void onViewPositionChanged(View mView, float swipeBackFraction, float SWIPE_BACK_FACTOR) {
                LLog.d(getTAG(), "onViewPositionChanged swipeBackFraction " + swipeBackFraction);
                float newY = screenH * swipeBackFraction;
                view.setTranslationY(newY);
            }

            @Override
            public void onViewSwipeFinished(View mView, boolean isEnd) {
                LLog.d(getTAG(), "onViewSwipeFinished");
            }
        });

        RadioGroup rb = findViewById(R.id.radio_group);
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
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_swipeback_layout;
    }

    @Override
    protected void onDestroy() {
        LLog.d(getTAG(), "onDestroy");
        super.onDestroy();
    }
}
