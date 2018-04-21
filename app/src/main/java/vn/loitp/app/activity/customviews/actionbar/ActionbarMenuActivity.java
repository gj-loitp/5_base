package vn.loitp.app.activity.customviews.actionbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayout.CollapsingToolbarLayoutActivity;
import vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayoutwithtablayout.CollapsingToolbarWithTabLayoutActivity;
import vn.loitp.app.activity.customviews.actionbar.lactionbar.LActionbarActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;

public class ActionbarMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_collapsingtoolbarlayout).setOnClickListener(this);
        findViewById(R.id.bt_l_actionbar).setOnClickListener(this);
        findViewById(R.id.bt_collapsingtoolbarwithtablayout).setOnClickListener(this);
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
        return R.layout.activity_menu_action_bar;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_collapsingtoolbarlayout:
                intent = new Intent(activity, CollapsingToolbarLayoutActivity.class);
                break;
            case R.id.bt_collapsingtoolbarwithtablayout:
                intent = new Intent(activity, CollapsingToolbarWithTabLayoutActivity.class);
                break;
            case R.id.bt_l_actionbar:
                intent = new Intent(activity, LActionbarActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
