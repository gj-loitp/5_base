package vn.loitp.app.activity.function.viewdraghelpersimple;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.LToast;

public class ViewDragHelperSimpleActivity1 extends BaseFontActivity implements VDHView.Callback {
    private VDHView vdhv;
    private TextView tv0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vdhv = (VDHView) findViewById(R.id.vdhv);
        tv0 = (TextView) findViewById(R.id.tv_0);
        vdhv.setCallback(this);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.showShort(activity, "Click");
            }
        });
        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.setAutoBackToOriginalPosition(!vdhv.isAutoBackToOriginalPosition());
            }
        });
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.maximize();
            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.minimize();
            }
        });
        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.setEnableAlpha(!vdhv.isEnableAlpha());
            }
        });
        findViewById(R.id.bt_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vdhv.getVisibilityHeaderView() == View.VISIBLE) {
                    vdhv.setVisibilityHeaderView(View.GONE);
                } else {
                    vdhv.setVisibilityHeaderView(View.VISIBLE);
                }
            }
        });
        findViewById(R.id.bt_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vdhv.getVisibilityBodyView() == View.VISIBLE) {
                    vdhv.setVisibilityBodyView(View.GONE);
                } else {
                    vdhv.setVisibilityBodyView(View.VISIBLE);
                }
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
        return R.layout.activity_view_draghelper_simple_1;
    }

    @Override
    public void onStateChange(VDHView.State state) {
        tv0.setText("onStateChange: " + state.name());
    }
}
