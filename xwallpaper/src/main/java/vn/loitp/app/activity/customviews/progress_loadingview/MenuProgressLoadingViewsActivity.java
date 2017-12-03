package vn.loitp.app.activity.customviews.progress_loadingview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view.AVLoadingIndicatorActivity;
import vn.loitp.app.activity.customviews.progress_loadingview.circularprogressbar.CircularProgressBarActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.app.utilities.LUIUtil;
import com.loitp.xwallpaper.R;

public class MenuProgressLoadingViewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_avloading_indicator_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AVLoadingIndicatorActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_circular_progress_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CircularProgressBarActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_progress_loading;
    }
}
