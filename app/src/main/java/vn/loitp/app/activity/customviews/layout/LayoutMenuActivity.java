package vn.loitp.app.activity.customviews.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.layout.autolinearlayout.AutoLinearLayoutActivity;
import vn.loitp.app.activity.customviews.layout.circularview.CircularViewActivity;
import vn.loitp.app.activity.customviews.layout.draggablepanel.DraggablePanelActivity;
import vn.loitp.app.activity.customviews.layout.draggableview.DraggableViewActivity;
import vn.loitp.app.activity.customviews.layout.dragueur.DragueurActivity;
import vn.loitp.app.activity.customviews.layout.elasticdragdismisslayout.ElasticDragDismissLayoutActivity;
import vn.loitp.app.activity.customviews.layout.ripplelayout.RippleLayoutActivity;
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.SwipeRefreshLayoutMenuActivity;
import vn.loitp.app.activity.customviews.layout.zoomlayout.ZoomLayoutActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class LayoutMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_draggable_panel).setOnClickListener(this);
        findViewById(R.id.bt_draggable_view).setOnClickListener(this);
        findViewById(R.id.bt_zoom_layout).setOnClickListener(this);
        findViewById(R.id.bt_ripple_layout).setOnClickListener(this);
        findViewById(R.id.bt_swipe_refresh_layout).setOnClickListener(this);
        findViewById(R.id.bt_dragueur).setOnClickListener(this);
        findViewById(R.id.bt_elastic_drag_dismiss_layout).setOnClickListener(this);
        findViewById(R.id.bt_circular_view).setOnClickListener(this);
        findViewById(R.id.bt_auto_linear_layout).setOnClickListener(this);
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
        return R.layout.activity_menu_layout;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_draggable_panel:
                intent = new Intent(activity, DraggablePanelActivity.class);
                break;
            case R.id.bt_draggable_view:
                intent = new Intent(activity, DraggableViewActivity.class);
                break;
            case R.id.bt_zoom_layout:
                intent = new Intent(activity, ZoomLayoutActivity.class);
                break;
            case R.id.bt_ripple_layout:
                intent = new Intent(activity, RippleLayoutActivity.class);
                break;
            case R.id.bt_swipe_refresh_layout:
                intent = new Intent(activity, SwipeRefreshLayoutMenuActivity.class);
                break;
            case R.id.bt_dragueur:
                intent = new Intent(activity, DragueurActivity.class);
                break;
            case R.id.bt_elastic_drag_dismiss_layout:
                intent = new Intent(activity, ElasticDragDismissLayoutActivity.class);
                break;
            case R.id.bt_circular_view:
                intent = new Intent(activity, CircularViewActivity.class);
                break;
            case R.id.bt_auto_linear_layout:
                intent = new Intent(activity, AutoLinearLayoutActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
