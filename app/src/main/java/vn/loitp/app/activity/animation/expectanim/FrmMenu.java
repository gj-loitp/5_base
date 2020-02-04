package vn.loitp.app.activity.animation.expectanim;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseFragment;

import vn.loitp.app.R;

public class FrmMenu extends BaseFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFrmRootView().findViewById(R.id.bt_0).setOnClickListener(v -> ((ExpectAnimActivity) getActivity()).addFrm(new FrmScroll(), true));
        getFrmRootView().findViewById(R.id.bt_1).setOnClickListener(v -> ((ExpectAnimActivity) getActivity()).addFrm(new FrmSample(), true));
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_expect_anim_menu;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }
}