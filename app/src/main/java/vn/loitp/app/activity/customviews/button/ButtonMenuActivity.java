package vn.loitp.app.activity.customviews.button;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.button.buttonloading.ButtonLoadingActivity;
import vn.loitp.app.activity.customviews.button.circularimageclick.CircularImageClickActivity;
import vn.loitp.app.activity.customviews.button.fbutton.FButtonActivity;
import vn.loitp.app.activity.customviews.button.goodview.GoodViewActivity;
import vn.loitp.app.activity.customviews.button.lbutton.LButtonActivity;
import vn.loitp.app.activity.customviews.button.shinebutton.ShineButtonActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class ButtonMenuActivity extends BaseFontActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_shine_button).setOnClickListener(this);
        findViewById(R.id.bt_f_button).setOnClickListener(this);
        findViewById(R.id.bt_circular_image_click).setOnClickListener(this);
        findViewById(R.id.bt_button_loading).setOnClickListener(this);
        findViewById(R.id.bt_goodview).setOnClickListener(this);
        findViewById(R.id.bt_l_button).setOnClickListener(this);
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
        return R.layout.activity_button_menu;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_shine_button:
                intent = new Intent(activity, ShineButtonActivity.class);
                break;
            case R.id.bt_f_button:
                intent = new Intent(activity, FButtonActivity.class);
                break;
            case R.id.bt_circular_image_click:
                intent = new Intent(activity, CircularImageClickActivity.class);
                break;
            case R.id.bt_button_loading:
                intent = new Intent(activity, ButtonLoadingActivity.class);
                break;
            case R.id.bt_goodview:
                intent = new Intent(activity, GoodViewActivity.class);
                break;
            case R.id.bt_l_button:
                intent = new Intent(activity, LButtonActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
