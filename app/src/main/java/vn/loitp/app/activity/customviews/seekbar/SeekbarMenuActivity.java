package vn.loitp.app.activity.customviews.seekbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.seekbar.circularseekbar.CircularSeekbarActivity;
import vn.loitp.app.activity.customviews.seekbar.verticalseekbar.VerticalSeekbarActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class SeekbarMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_circularseekbar_seekbar).setOnClickListener(this);
        findViewById(R.id.bt_vertical_seekbar).setOnClickListener(this);
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
        return R.layout.activity_menu_seekbar;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_circularseekbar_seekbar:
                intent = new Intent(activity, CircularSeekbarActivity.class);
                break;
            case R.id.bt_vertical_seekbar:
                intent = new Intent(activity, VerticalSeekbarActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
