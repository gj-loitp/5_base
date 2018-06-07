package vn.loitp.app.activity.customviews.draggableflipview;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

//https://github.com/ssk5460/DraggableFlipView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2509
public class DraggableFlipViewActivity extends BaseFontActivity {

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
    protected int setLayoutResourceId() {
        return R.layout.activity_draggable_flipview;
    }

}
