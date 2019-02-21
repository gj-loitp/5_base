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
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vdhv = (VDHView) findViewById(R.id.vdhv);
        tv0 = (TextView) findViewById(R.id.tv_0);
        tv1 = (TextView) findViewById(R.id.tv_1);
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
        findViewById(R.id.bt_2_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.minimizeBottomLeft();
            }
        });
        findViewById(R.id.bt_2_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.minimizeBottomRight();
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
                vdhv.toggleShowHideHeaderView();
            }
        });
        findViewById(R.id.bt_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.toggleShowHideBodyView();
            }
        });
        findViewById(R.id.bt_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.smoothSlideTo(300, 600);
            }
        });
        findViewById(R.id.bt_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.setEnableRevertMaxSize(!vdhv.isEnableRevertMaxSize());
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

    @Override
    public void onViewPositionChanged(int left, int top, float dragOffset) {
        tv1.setText("onViewPositionChanged left: " + left + ", top: " + top + ", dragOffset: " + dragOffset);
    }
}
