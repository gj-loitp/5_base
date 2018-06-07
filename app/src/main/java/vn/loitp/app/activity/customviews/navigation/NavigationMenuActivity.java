package vn.loitp.app.activity.customviews.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.app.activity.customviews.navigation.arcnavigationview.ArcNavigationViewActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class NavigationMenuActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_arc_navigation).setOnClickListener(this);
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
        return R.layout.activity_menu_navigation_view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_arc_navigation:
                intent = new Intent(activity, ArcNavigationViewActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
