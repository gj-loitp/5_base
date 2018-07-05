package vn.loitp.app.activity.customviews.button.lbutton;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.button.lbutton.LButton;

public class LButtonActivity extends BaseFontActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LButton bt0 = (LButton) findViewById(R.id.bt_0);
        bt0.setOnClickListener(this);
        LButton bt1 = (LButton) findViewById(R.id.bt_1);
        bt1.setPressedDrawable(R.drawable.circle_color_primary);
        bt1.setOnClickListener(this);
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
        return R.layout.activity_l_button;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_0:
            case R.id.bt_1:
                LToast.show(activity, "Click");
                break;
        }
    }
}
