package vn.loitp.app.activity.customviews.videoview.exoplayer2withdragpanel3;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.layout.draggablepanel.FrmTest;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.draggablepanel.DraggableListener;
import vn.loitp.views.draggablepanel.DraggablePanel;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmContainer extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private DraggablePanel draggablePanel;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_container, container, false);
        draggablePanel = (DraggablePanel) view.findViewById(R.id.draggable_panel);
        initializeDraggablePanel();

        draggablePanel.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
                LLog.d(TAG, "onMaximized");
            }

            @Override
            public void onMinimized() {
                LLog.d(TAG, "onMinimized");
            }

            @Override
            public void onClosedToLeft() {
                LLog.d(TAG, "onClosedToLeft");
            }

            @Override
            public void onClosedToRight() {
                LLog.d(TAG, "onClosedToRight");
            }
        });
        return view;
    }

    private void initializeDraggablePanel() throws Resources.NotFoundException {
        FrmTest frmTop = new FrmTest();
        FrmTest frmBottom = new FrmTest();

        draggablePanel.setFragmentManager(getActivity().getSupportFragmentManager());
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