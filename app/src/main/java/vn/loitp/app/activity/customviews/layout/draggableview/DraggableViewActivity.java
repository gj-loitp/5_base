package vn.loitp.app.activity.customviews.layout.draggableview;

import android.app.Activity;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.layout.draggablepanel.DraggableListener;
import vn.loitp.views.layout.draggablepanel.DraggableView;

public class DraggableViewActivity extends BaseActivity {
    private DraggableView draggableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        draggableView = (DraggableView) findViewById(R.id.draggable_view);

        draggableView.setClickToMaximizeEnabled(true);
        draggableView.setClickToMinimizeEnabled(true);
        draggableView.setHorizontalAlphaEffectEnabled(true);

        draggableView.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
                LLog.d(TAG, "onMaximized");
            }

            @Override
            public void onMinimized() {
                LLog.d(TAG, "onMinimized");
            }

            @Override
            public void onClosedToLeft() {
                LLog.d(TAG, "onClosedToLeft");
            }

            @Override
            public void onClosedToRight() {
                LLog.d(TAG, "onClosedToRight");
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
        return R.layout.activity_draggable_view;
    }
}
