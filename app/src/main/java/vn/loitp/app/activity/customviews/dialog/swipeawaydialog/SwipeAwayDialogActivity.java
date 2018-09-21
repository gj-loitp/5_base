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
                show1();
                break;
        }
    }

    private void show1() {
        SADilog saDilog = new SADilog();
        Bundle bundle = new Bundle();
        bundle.putInt(SADilog.KEY, 1);
        saDilog.setArguments(bundle);
        saDilog.show(getSupportFragmentManager(), SADilog.class.getSimpleName());
    }
}
