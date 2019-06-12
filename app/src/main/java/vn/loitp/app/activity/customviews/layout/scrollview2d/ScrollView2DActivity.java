package vn.loitp.app.activity.customviews.layout.scrollview2d;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.layout.swipablelayout.SwipeableLayout;

public class ScrollView2DActivity extends BaseFontActivity {

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
        return R.layout.activity_scrollview_2d;
    }
}
