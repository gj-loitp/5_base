package vn.loitp.app.activity.customviews.popupmenu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LPopupMenu;
import com.views.LToast;

import loitp.basemaster.R;

public class PopupMenuActivity extends BaseFontActivity implements View.OnClickListener {

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
                        LToast.INSTANCE.show(activity, menuItem.getTitle().toString());
                    }
                });
                break;
        }
    }
}
