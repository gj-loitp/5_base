package vn.loitp.app.activity.customviews.menu.residemenu;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annotation.LogTag;
import com.core.base.BaseFragment;
import com.views.menu.residemenu.ResideMenu;

import vn.loitp.app.R;

@LogTag("HomeFragment")
public class HomeFragment extends BaseFragment {
    private ResideMenu resideMenu;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.reside_menu_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews();
    }

    private void setUpViews() {
        final ResideMenuActivity parentActivity = (ResideMenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        getFrmRootView().findViewById(R.id.btOpenMenu).setOnClickListener(view -> resideMenu.openMenu(ResideMenu.DIRECTION_LEFT));

        FrameLayout ignored_view = getFrmRootView().findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);
    }

}
