package vn.loitp.app.activity.customviews.button;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.button.autosizebutton.AutoSizeButtonActivity;
import vn.loitp.app.activity.customviews.button.buttonloading.ButtonLoadingActivity;
import vn.loitp.app.activity.customviews.button.circularimageclick.CircularImageClickActivity;
import vn.loitp.app.activity.customviews.button.fbutton.FButtonActivity;
import vn.loitp.app.activity.customviews.button.goodview.GoodViewActivity;
import vn.loitp.app.activity.customviews.button.lbutton.LButtonActivity;
import vn.loitp.app.activity.customviews.button.roundedbutton.RoundedButtonActivity;
import vn.loitp.app.activity.customviews.button.shinebutton.ShineButtonActivity;

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
        findViewById(R.id.bt_auto_size_button).setOnClickListener(this);
        findViewById(R.id.bt_rounded_button).setOnClickListener(this);
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
                intent = new Intent(getActivity(), ShineButtonActivity.class);
                break;
            case R.id.bt_f_button:
                intent = new Intent(getActivity(), FButtonActivity.class);
                break;
            case R.id.bt_circular_image_click:
                intent = new Intent(getActivity(), CircularImageClickActivity.class);
                break;
            case R.id.bt_button_loading:
                intent = new Intent(getActivity(), ButtonLoadingActivity.class);
                break;
            case R.id.bt_goodview:
                intent = new Intent(getActivity(), GoodViewActivity.class);
                break;
            case R.id.bt_l_button:
                intent = new Intent(getActivity(), LButtonActivity.class);
                break;
            case R.id.bt_auto_size_button:
                intent = new Intent(getActivity(), AutoSizeButtonActivity.class);
                break;
            case R.id.bt_rounded_button:
                intent = new Intent(getActivity(), RoundedButtonActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.INSTANCE.tranIn(getActivity());
        }
    }
}
