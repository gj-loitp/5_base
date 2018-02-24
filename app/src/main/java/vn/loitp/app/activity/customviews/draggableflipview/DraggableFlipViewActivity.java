package vn.loitp.app.activity.customviews.draggableflipview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.dialog.iosdialog.DialogIOSActivity;
import vn.loitp.app.activity.customviews.dialog.originaldialog.DialogOriginalActivity;
import vn.loitp.app.activity.customviews.dialog.prettydialog.PrettyDialogActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

//https://github.com/ssk5460/DraggableFlipView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2509
public class DraggableFlipViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_draggable_flipview;
    }

}
