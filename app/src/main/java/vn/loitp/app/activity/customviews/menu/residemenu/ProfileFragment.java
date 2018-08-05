package vn.loitp.app.activity.customviews.menu.residemenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午1:31
 * Mail: specialcyci@gmail.com
 */
public class ProfileFragment extends BaseFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.reside_menu_profile;
    }

}
