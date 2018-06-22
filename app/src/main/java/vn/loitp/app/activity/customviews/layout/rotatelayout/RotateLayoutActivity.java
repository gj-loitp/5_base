package vn.loitp.app.activity.customviews.layout.rotatelayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.layout.autolinearlayout.AutoLinearLayoutActivity;
import vn.loitp.app.activity.customviews.layout.circularview.CircularViewActivity;
import vn.loitp.app.activity.customviews.layout.constraintlayout.ConstraintlayoutMenuActivity;
import vn.loitp.app.activity.customviews.layout.draggablepanel.DraggablePanelActivity;
import vn.loitp.app.activity.customviews.layout.draggableview.DraggableViewActivity;
import vn.loitp.app.activity.customviews.layout.dragueur.DragueurActivity;
import vn.loitp.app.activity.customviews.layout.elasticdragdismisslayout.ElasticDragDismissLayoutActivity;
import vn.loitp.app.activity.customviews.layout.floatdraglayout.FloatDragLayoutActivity;
import vn.loitp.app.activity.customviews.layout.heartlayout.HeartLayoutActivity;
import vn.loitp.app.activity.customviews.layout.ripplelayout.RippleLayoutActivity;
import vn.loitp.app.activity.customviews.layout.swipablelayout.SwipableLayoutActivity;
import vn.loitp.app.activity.customviews.layout.swipeablelayout.SwipeableLayoutActivity;
import vn.loitp.app.activity.customviews.layout.swipebacklayout.SwipeBackLayoutActivity;
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.SwipeRefreshLayoutMenuActivity;
import vn.loitp.app.activity.customviews.layout.zoomlayout.ZoomLayoutActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.views.layout.rotatelayout.RotateLayout;

public class RotateLayoutActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RotateLayout rotateLayout = (RotateLayout) findViewById(R.id.rotate_layout);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int angle = LDeviceUtil.getRandomNumber(360);
                rotateLayout.setAngle(angle);
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
        return R.layout.activity_rotate_layout;
    }
}
