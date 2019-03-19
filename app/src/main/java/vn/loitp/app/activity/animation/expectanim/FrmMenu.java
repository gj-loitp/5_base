package vn.loitp.app.activity.animation.expectanim;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;

import static vn.loitp.function.expectanim.core.Expectations.height;
import static vn.loitp.function.expectanim.core.Expectations.scale;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmMenu extends BaseFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frmRootView.findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ExpectAnimActivity) getActivity()).addFrm(new FrmScroll(), true);
            }
        });
        frmRootView.findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ExpectAnimActivity) getActivity()).addFrm(new FrmSample(), true);
            }
        });
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_expect_anim_menu;
    }
}