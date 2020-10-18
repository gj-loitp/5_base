package vn.loitp.app.activity.customviews.layout.coordinatorlayout;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.views.LToast;

import vn.loitp.app.R;

@LayoutId(R.layout.frm_coordinator_1)
@LogTag("RelativePopupWindowActivity")
public class FrmCoordinator1 extends BaseFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = view.findViewById(R.id.tl);
        // Add five tabs.  Three have icons and two have text titles
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_add_black_48dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_bug_report_black_48dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_chat_black_48dp));
        tabLayout.addTab(tabLayout.newTab().setText("Send"));
        tabLayout.addTab(tabLayout.newTab().setText("Send & Post"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    LToast.INSTANCE.showShortInformation("Tab " + tabLayout.getSelectedTabPosition(), true);
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    LToast.INSTANCE.showShortInformation("Tab " + tabLayout.getSelectedTabPosition(), true);
                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    LToast.INSTANCE.showShortInformation("Tab " + tabLayout.getSelectedTabPosition(), true);
                } else if (tabLayout.getSelectedTabPosition() == 3) {
                    LToast.INSTANCE.showShortInformation("Tab " + tabLayout.getSelectedTabPosition(), true);
                } else if (tabLayout.getSelectedTabPosition() == 4) {
                    LToast.INSTANCE.showShortInformation("Tab " + tabLayout.getSelectedTabPosition(), true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
