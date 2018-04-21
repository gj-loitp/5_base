package vn.loitp.app.activity.customviews.scratchview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.scratchview.scratchviewimage.ScratchViewImageActivity;
import vn.loitp.app.activity.customviews.scratchview.scratchviewtext.ScratchViewTextActivity;
import vn.loitp.app.activity.customviews.seekbar.circularseekbar.CircularSeekbarActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class ScratchViewMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_scratchview_image).setOnClickListener(this);
        findViewById(R.id.bt_scratchview_text).setOnClickListener(this);
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
        return R.layout.activity_menu_scratchview;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_scratchview_image:
                intent = new Intent(activity, ScratchViewImageActivity.class);
                break;
            case R.id.bt_scratchview_text:
                intent = new Intent(activity, ScratchViewTextActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
