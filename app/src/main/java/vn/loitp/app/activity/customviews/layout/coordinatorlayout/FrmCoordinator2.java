package vn.loitp.app.activity.customviews.layout.coordinatorlayout;

import com.core.base.BaseFragment;

import org.jetbrains.annotations.Nullable;

import loitp.basemaster.R;

public class FrmCoordinator2 extends BaseFragment {
    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_coordinator_2;
    }

    @Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }
}
