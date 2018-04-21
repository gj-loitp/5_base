package vn.loitp.app.activity.customviews.popupmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.views.LToast;

public class PopupMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_show_1).setOnClickListener(this);
        findViewById(R.id.bt_show_2).setOnClickListener(this);
        findViewById(R.id.bt_show_3).setOnClickListener(this);
        findViewById(R.id.bt_show_4).setOnClickListener(this);
        findViewById(R.id.bt_show_5).setOnClickListener(this);
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
        return R.layout.activity_popup_menu;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_show_1:
            case R.id.bt_show_2:
            case R.id.bt_show_3:
            case R.id.bt_show_4:
            case R.id.bt_show_5:
                LPopupMenu.show(activity, v, R.menu.menu_popup, new LPopupMenu.CallBack() {
                    @Override
                    public void clickOnItem(MenuItem menuItem) {
                        LToast.show(activity, menuItem.getTitle().toString());
                    }
                });
                break;
        }
    }
}
