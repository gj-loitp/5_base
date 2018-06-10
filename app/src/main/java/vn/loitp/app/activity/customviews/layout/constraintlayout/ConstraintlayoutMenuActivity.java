package vn.loitp.app.activity.customviews.layout.constraintlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.layout.constraintlayout.custombehavior.CustomBehaviorActivity;
import vn.loitp.app.activity.customviews.layout.constraintlayout.demo.ConstraintlayoutDemoActivity;
import vn.loitp.app.activity.customviews.layout.constraintlayout.fabandsnackbar.FabAndSnackbarActivity;
import vn.loitp.app.activity.customviews.layout.constraintlayout.fabfollowswiidget.FabFollowWidgetActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class ConstraintlayoutMenuActivity extends BaseFontActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_demo).setOnClickListener(this);
        findViewById(R.id.bt_fab_n_snackbar).setOnClickListener(this);
        findViewById(R.id.bt_fab_follow_widget).setOnClickListener(this);
        findViewById(R.id.bt_custom_behavior).setOnClickListener(this);
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
        return R.layout.activity_constraintlayout_menu;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_demo:
                intent = new Intent(activity, ConstraintlayoutDemoActivity.class);
                break;
            case R.id.bt_fab_n_snackbar:
                intent = new Intent(activity, FabAndSnackbarActivity.class);
                break;
            case R.id.bt_fab_follow_widget:
                intent = new Intent(activity, FabFollowWidgetActivity.class);
                break;
            case R.id.bt_custom_behavior:
                intent = new Intent(activity, CustomBehaviorActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
