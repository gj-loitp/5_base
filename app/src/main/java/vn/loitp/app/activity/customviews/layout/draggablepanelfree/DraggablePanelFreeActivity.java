package vn.loitp.app.activity.customviews.layout.draggablepanelfree;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;
import vn.loitp.views.layout.draggablepanelfree.DraggablePanelFreeLayout;

public class DraggablePanelFreeActivity extends BaseFontActivity {
    private DraggablePanelFreeLayout dpfl;
    private TextView tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dpfl = (DraggablePanelFreeLayout) findViewById(R.id.dpfl);
        tvState = (TextView) findViewById(R.id.tv_state);
        findViewById(R.id.bt_maximize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpfl.maximize();
            }
        });
        findViewById(R.id.bt_minimize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpfl.minimize();
            }
        });
        dpfl.setCallback(new DraggablePanelFreeLayout.Callback() {
            @Override
            public void onStateChange(DraggablePanelFreeLayout.State state) {
                tvState.setText("onStateChange " + state.name());
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
        return R.layout.activity_draggable_panel_free;
    }
}
