package vn.loitp.app.activity.customviews.layout.draggablepanel;

import android.content.res.Resources;
import android.os.Bundle;

import com.annotation.IsFullScreen;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.views.layout.draggablepanel.DraggableListener;
import com.views.layout.draggablepanel.DraggablePanel;

import vn.loitp.app.R;

@LogTag("DraggablePanelActivity")
@IsFullScreen(false)
public class DraggablePanelActivity extends BaseFontActivity {
    private DraggablePanel draggablePanel;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_draggable_panel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        draggablePanel = findViewById(R.id.draggablePanel);
        initializeDraggablePanel();

        draggablePanel.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
            }

            @Override
            public void onMinimized() {
            }

            @Override
            public void onClosedToLeft() {
            }

            @Override
            public void onClosedToRight() {
            }
        });
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
