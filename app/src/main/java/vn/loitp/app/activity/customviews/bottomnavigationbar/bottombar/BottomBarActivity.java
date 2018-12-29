package vn.loitp.app.activity.customviews.bottomnavigationbar.bottombar;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.bottombar.LBottomBar;

public class BottomBarActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(LStoreUtil.readTxtFromRawFolder(activity, R.raw.loitp));
        LBottomBar lBottomBar = (LBottomBar) findViewById(R.id.bottom_bar);
        lBottomBar.setOnItemClick(new LBottomBar.Callback() {
            @Override
            public void OnClickItem(int position) {
                ToastUtils.showShort("Touch " + position);
            }
        });
        findViewById(R.id.bt_blur_view_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.getBlurView().setOverlayColor(ContextCompat.getColor(activity, R.color.RedTrans));
            }
        });
        findViewById(R.id.bt_blur_view_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.getBlurView().setOverlayColor(ContextCompat.getColor(activity, R.color.GreenTrans));
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
        return R.layout.activity_bottom_bar;
    }
}
