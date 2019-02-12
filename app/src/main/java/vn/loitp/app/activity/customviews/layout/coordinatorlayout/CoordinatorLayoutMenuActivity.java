package vn.loitp.app.activity.customviews.layout.coordinatorlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

//http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
public class CoordinatorLayoutMenuActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_0).setOnClickListener(this);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        findViewById(R.id.bt_4).setOnClickListener(this);

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
        return R.layout.activity_menu_coordinator_layout;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_0:
                intent = new Intent(activity, CoordinatorLayoutWithImageViewActivity.class);
                break;
            case R.id.bt_1:
                intent = new Intent(activity, CoordinatorLayoutSampleActivity.class);
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_0);
                break;
            case R.id.bt_2:
                intent = new Intent(activity, CoordinatorLayoutSampleActivity.class);
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_1);
                break;
            case R.id.bt_3:
                intent = new Intent(activity, CoordinatorLayoutSampleActivity.class);
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_2);
                break;
            case R.id.bt_4:
                intent = new Intent(activity, CoordinatorLayoutSampleActivity.class);
                intent.putExtra(CoordinatorLayoutSampleActivity.KEY, CoordinatorLayoutSampleActivity.VALUE_3);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
