package vn.loitp.app.activity.customviews.menu.residemenu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.views.menu.residemenu.ResideMenu;

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
        ResideMenuActivity parentActivity = (ResideMenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        frmRootView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        // add gesture operation's ignored views
        FrameLayout ignored_view = (FrameLayout) frmRootView.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);
    }

}
