package vn.loitp.app.activity.customviews.layout.draggablepanel;

import android.content.res.Resources;
import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.views.layout.draggablepanel.DraggableListener;
import com.views.layout.draggablepanel.DraggablePanel;

import loitp.basemaster.R;

public class DraggablePanelActivity extends BaseFontActivity {
    private DraggablePanel draggablePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        draggablePanel = (DraggablePanel) findViewById(R.id.draggable_panel);
        initializeDraggablePanel();

        draggablePanel.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
                LLog.INSTANCE.d(getTAG(), "onMaximized");
            }

            @Override
            public void onMinimized() {
                LLog.INSTANCE.d(getTAG(), "onMinimized");
            }

            @Override
            public void onClosedToLeft() {
                LLog.INSTANCE.d(getTAG(), "onClosedToLeft");
            }

            @Override
            public void onClosedToRight() {
                LLog.INSTANCE.d(getTAG(), "onClosedToRight");
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
        return R.layout.activity_draggable_panel;
    }

    private void initializeDraggablePanel() throws Resources.NotFoundException {
        FrmTestTop frmTop = new FrmTestTop();
        FrmTestBottom frmBottom = new FrmTestBottom();

        draggablePanel.setFragmentManager(getSupportFragmentManager());
        draggablePanel.setTopFragment(frmTop);
        draggablePanel.setBottomFragment(frmBottom);

        //draggablePanel.setXScaleFactor(xScaleFactor);
        //draggablePanel.setYScaleFactor(yScaleFactor);
        draggablePanel.setTopViewHeight(600);
        //draggablePanel.setTopFragmentMarginRight(topViewMarginRight);
        //draggablePanel.setTopFragmentMarginBottom(topViewMargnBottom);
        draggablePanel.setClickToMaximizeEnabled(false);
        draggablePanel.setClickToMinimizeEnabled(false);

        draggablePanel.initializeView();
    }
}
