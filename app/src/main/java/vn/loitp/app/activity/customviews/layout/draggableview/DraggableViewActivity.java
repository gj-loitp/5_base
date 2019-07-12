package vn.loitp.app.activity.customviews.layout.draggableview;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.views.layout.draggablepanel.DraggableListener;
import com.views.layout.draggablepanel.DraggableView;

import loitp.basemaster.R;

public class DraggableViewActivity extends BaseFontActivity {
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
                LLog.INSTANCE.d(getTAG(), "onMaximized");
            }

            @Override
            public void onMinimized() {
                LLog.INSTANCE.d(getTAG(), "onMinimized");
            }

            @Override
            public void onClosedToLeft() {
                LLog.INSTANCE.d(getTAG(), "onClosedToLeft");
            }

            @Override
            public void onClosedToRight() {
                LLog.INSTANCE.d(getTAG(), "onClosedToRight");
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
