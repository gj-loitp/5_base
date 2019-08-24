package vn.loitp.app.activity.customviews.menu.residemenu;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseFragment;

import loitp.basemaster.R;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午3:28
 * Mail: specialcyci@gmail.com
 */
public class SettingsFragment extends BaseFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.reside_menu_setting;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }
}
