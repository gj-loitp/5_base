package vn.loitp.app.activity.customviews.button.roundedbutton;

import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.LToast;

public class RoundedButtonActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.showShort(activity, "Click");
            }
        });
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.showShort(activity, "Click");
            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.showShort(activity, "Click");
            }
        });
        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.showShort(activity, "Click");
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
        return R.layout.activity_rounded_button;
    }
}
