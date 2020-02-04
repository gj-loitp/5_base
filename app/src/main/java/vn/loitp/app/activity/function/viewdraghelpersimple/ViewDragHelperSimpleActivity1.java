package vn.loitp.app.activity.function.viewdraghelpersimple;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.views.LToast;

import vn.loitp.app.R;

public class ViewDragHelperSimpleActivity1 extends BaseFontActivity implements VDHView.Callback {
    private VDHView vdhv;
    private TextView tv0;
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vdhv = (VDHView) findViewById(R.id.vdhv);
        tv0 = (TextView) findViewById(R.id.tv_0);
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        vdhv.setCallback(this);
        findViewById(R.id.bt_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.INSTANCE.showShort(getActivity(), "Click");
            }
        });
        findViewById(R.id.bt_maximize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.maximize();
            }
        });
        findViewById(R.id.bt_minimize_bottom_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.minimizeBottomLeft();
            }
        });
        findViewById(R.id.bt_minimize_bottom_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.minimizeBottomRight();
            }
        });
        findViewById(R.id.bt_minimize_top_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.minimizeTopRight();
            }
        });
        findViewById(R.id.bt_minimize_top_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.minimizeTopLeft();
            }
        });
        findViewById(R.id.bt_alpha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.setEnableAlpha(!vdhv.isEnableAlpha());
            }
        });
        findViewById(R.id.bt_show_hide_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.toggleShowHideHeaderView();
            }
        });
        findViewById(R.id.bt_show_hide_body).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.toggleShowHideBodyView();
            }
        });
        findViewById(R.id.bt_slide_to_position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.smoothSlideTo(300, 600);
            }
        });
        findViewById(R.id.bt_revert_max).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vdhv.isEnableRevertMaxSize()) {
                    vdhv.setEnableRevertMaxSize(false);
                    findViewById(R.id.bt_maximize).setVisibility(View.GONE);
                    if (vdhv.isMinimized()) {
                        findViewById(R.id.bt_minimize_top_right).setVisibility(View.VISIBLE);
                        findViewById(R.id.bt_minimize_top_left).setVisibility(View.VISIBLE);
                    }
                } else {
                    vdhv.setEnableRevertMaxSize(true);
                    findViewById(R.id.bt_maximize).setVisibility(View.VISIBLE);
                    findViewById(R.id.bt_minimize_top_right).setVisibility(View.GONE);
                    findViewById(R.id.bt_minimize_top_left).setVisibility(View.GONE);
                }
            }
        });
        findViewById(R.id.bt_appear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.appear();
            }
        });
        findViewById(R.id.bt_disappear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.dissappear();
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
    public void onPartChange(VDHView.Part part) {
        tv2.setText("onPartChange: " + part.name());
    }

    @Override
    public void onViewPositionChanged(int left, int top, float dragOffset) {
        tv1.setText("onViewPositionChanged left: " + left + ", top: " + top + ", dragOffset: " + dragOffset);
    }

    @Override
    public void onOverScroll(VDHView.State state, VDHView.Part part) {
        vdhv.dissappear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vdhv.onPause();
    }
}
