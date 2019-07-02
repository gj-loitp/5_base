package vn.loitp.app.activity.customviews.actionbar.lactionbar;

import android.os.Bundle;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.actionbar.LActionBar;

public class LActionbarActivity extends BaseFontActivity {

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
        final LActionBar lActionBar = findViewById(R.id.l_action_bar);
        final TextView tv = findViewById(R.id.tv);
        tv.setText(LStoreUtil.readTxtFromRawFolder(activity, R.raw.lactionbar));

        lActionBar.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                LToast.show(activity, "onClickMenu");
            }
        });
        lActionBar.showMenuIcon();
        lActionBar.setImageMenuIcon(R.mipmap.ic_launcher);
        lActionBar.setTvTitle("Demo LActionbarActivity");
    }
}
