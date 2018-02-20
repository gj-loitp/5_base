package vn.loitp.app.activity.customviews.layout.draggablepanel;

import android.app.Activity;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.draggablepanel.DraggableListener;
import vn.loitp.views.draggablepanel.DraggableView;

public class DraggablePanelActivity extends BaseActivity {
    private DraggableView draggableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        draggableView = (DraggableView) findViewById(R.id.draggable_view);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_draggable_panel;
    }
}
