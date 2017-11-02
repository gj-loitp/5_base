package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view._lib.avi.AVLoadingIndicatorView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.livestar.R;

public class TTTAPIPageListActivity extends BaseActivity {
    private TextView tv;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
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
        return R.layout.activity_api_ttt_page_list;
    }
}
