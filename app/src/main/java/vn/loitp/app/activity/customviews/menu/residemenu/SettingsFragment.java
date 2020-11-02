package vn.loitp.app.activity.customviews.menu.residemenu;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annotation.LogTag;
import com.core.base.BaseFragment;

import vn.loitp.app.R;

@LogTag("SettingsFragment")
public class SettingsFragment extends BaseFragment {

    @Override
    protected int setLayoutResourceId() {
        return R.layout.reside_menu_setting;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
