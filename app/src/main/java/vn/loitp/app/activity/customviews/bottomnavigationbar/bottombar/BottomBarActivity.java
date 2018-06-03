package vn.loitp.app.activity.customviews.bottomnavigationbar.bottombar;

import android.os.Bundle;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.activity.BaseFontActivity;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.bottombar.lib.LBottomBar;

public class BottomBarActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(LStoreUtil.readTxtFromRawFolder(activity, R.raw.loitp));

        setupBottomBar();
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
        return R.layout.activity_bottom_bar;
    }

    private void setupBottomBar() {
        LBottomBar lBottomBar = (LBottomBar) findViewById(R.id.bottom_bar);
        lBottomBar.setOnItemClick(new LBottomBar.Callback() {
            @Override
            public void OnClickItem(int position) {
                ToastUtils.showShort("Touch " + position);
            }
        });
    }
}
