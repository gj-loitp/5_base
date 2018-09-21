package vn.loitp.app.activity.customviews.dialog.swipeawaydialog;

import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

//https://github.com/kakajika/SwipeAwayDialog
public class SwipeAwayDialogActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_show_1).setOnClickListener(this);
        findViewById(R.id.bt_show_2).setOnClickListener(this);
        findViewById(R.id.bt_show_3).setOnClickListener(this);
        findViewById(R.id.bt_show_list).setOnClickListener(this);
        findViewById(R.id.bt_progress_dialog).setOnClickListener(this);
        findViewById(R.id.bt_custom_dialog).setOnClickListener(this);
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
        return R.layout.activity_swipe_away_dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_show_1:
                show(SADilog.KEY_1);
                break;
            case R.id.bt_show_2:
                show(SADilog.KEY_2);
                break;
            case R.id.bt_show_3:
                show(SADilog.KEY_3);
                break;
            case R.id.bt_show_list:
                show(SADilog.KEY_4);
                break;
            case R.id.bt_progress_dialog:
                show(SADilog.KEY_5);
                break;
            case R.id.bt_custom_dialog:
                show(SADilog.KEY_6);
                break;
        }
    }

    private void show(int key) {
        SADilog saDilog = new SADilog();
        Bundle bundle = new Bundle();
        bundle.putInt(SADilog.KEY, key);
        saDilog.setArguments(bundle);
        saDilog.show(getSupportFragmentManager(), SADilog.class.getSimpleName());
    }
}
