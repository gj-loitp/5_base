package vn.loitp.app.activity.customviews.actionbar.lactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.actionbar.lib.LActionBar;

public class LActionbarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
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
        return R.layout.activity_action_bar;
    }

    private void setupActionBar() {
        LActionBar lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(LStoreUtil.readTxtFromRawFolder(activity, R.raw.lactionbar));

        lActionBar.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                ToastUtils.showShort("onClickMenu");
            }
        });
        lActionBar.showMenuIcon();
        lActionBar.setImageMenuIcon(R.mipmap.ic_launcher);
        lActionBar.setTvTitle("Demo LActionbarActivity");
    }
}
