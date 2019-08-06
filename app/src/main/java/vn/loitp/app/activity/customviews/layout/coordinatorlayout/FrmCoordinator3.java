package vn.loitp.app.activity.customviews.layout.coordinatorlayout;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseFragment;

import loitp.basemaster.R;

public class FrmCoordinator3 extends BaseFragment {
    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_coordinator_3;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }
}
