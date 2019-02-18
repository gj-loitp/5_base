package vn.loitp.app.activity.customviews.layout.draggablepanelfree;

import android.content.res.Resources;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.layout.draggablepanel.FrmTestBottom;
import vn.loitp.app.activity.customviews.layout.draggablepanel.FrmTestTop;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.layout.draggablepanel.DraggableListener;
import vn.loitp.views.layout.draggablepanel.DraggablePanel;

public class DraggablePanelFreeActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
