package vn.loitp.app.activity.customviews.layout.heartlayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import loitp.basemaster.R;
import vn.loitp.app.activity.BaseFontActivity;
import vn.loitp.app.activity.customviews.layout.autolinearlayout.AutoLinearLayoutActivity;
import vn.loitp.app.activity.customviews.layout.circularview.CircularViewActivity;
import vn.loitp.app.activity.customviews.layout.constraintlayout.ConstraintlayoutActivity;
import vn.loitp.app.activity.customviews.layout.draggablepanel.DraggablePanelActivity;
import vn.loitp.app.activity.customviews.layout.draggableview.DraggableViewActivity;
import vn.loitp.app.activity.customviews.layout.dragueur.DragueurActivity;
import vn.loitp.app.activity.customviews.layout.elasticdragdismisslayout.ElasticDragDismissLayoutActivity;
import vn.loitp.app.activity.customviews.layout.ripplelayout.RippleLayoutActivity;
import vn.loitp.app.activity.customviews.layout.swipablelayout.SwipableLayoutActivity;
import vn.loitp.app.activity.customviews.layout.swipeablelayout.SwipeableLayoutActivity;
import vn.loitp.app.activity.customviews.layout.swipebacklayout.SwipeBackLayoutActivity;
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.SwipeRefreshLayoutMenuActivity;
import vn.loitp.app.activity.customviews.layout.zoomlayout.ZoomLayoutActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.views.layout.heartlayout.HeartLayout;

public class HeartLayoutActivity extends BaseFontActivity {
    private Random mRandom = new Random();
    private HeartLayout mHeartLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeartLayout.addHeart(randomColor());
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
        return R.layout.activity_heart_layout;
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }
}
