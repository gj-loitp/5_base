package vn.loitp.app.activity.customviews.menu.residemenu;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseFragment;
import com.views.menu.residemenu.ResideMenu;

import loitp.basemaster.R;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午1:33
 * Mail: specialcyci@gmail.com
 */
public class HomeFragment extends BaseFragment {
    private ResideMenu resideMenu;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.reside_menu_home;
    }

    private void setUpViews() {
        final ResideMenuActivity parentActivity = (ResideMenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        getFrmRootView().findViewById(R.id.btn_open_menu).setOnClickListener(view -> resideMenu.openMenu(ResideMenu.DIRECTION_LEFT));

        // add gesture operation's ignored views
        FrameLayout ignored_view = (FrameLayout) getFrmRootView().findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }
}
