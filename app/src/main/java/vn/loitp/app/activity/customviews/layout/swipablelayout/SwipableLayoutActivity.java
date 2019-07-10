package vn.loitp.app.activity.customviews.layout.swipablelayout;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.views.layout.swipablelayout.SwipeableLayout;

import loitp.basemaster.R;

//https://github.com/SerhatSurguvec/SwipableLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2666
public class SwipableLayoutActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        SwipeableLayout swipeableLayout = (SwipeableLayout) findViewById(R.id.swipableLayout);
        swipeableLayout.setOnLayoutCloseListener(new SwipeableLayout.OnLayoutCloseListener() {
            @Override
            public void OnLayoutClosed() {
                LLog.INSTANCE.d(TAG, "OnLayoutClosed");
                finish();
            }
        });
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
        return R.layout.activity_swipable_layout;
    }
}
