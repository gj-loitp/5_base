package vn.loitp.app.activity.customviews.layout.draggablepanelfree;

import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.layout.draggablepanelfree.DraggablePanelFreeLayout;

public class DraggablePanelFreeActivity extends BaseFontActivity {
    private DraggablePanelFreeLayout dpfl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dpfl = (DraggablePanelFreeLayout) findViewById(R.id.dpfl);
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
