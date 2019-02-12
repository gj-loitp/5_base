package vn.loitp.app.activity.customviews.layout.coordinatorlayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.views.LToast;

public class FrmCoordinator1 extends BaseFragment {
    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_coordinator_1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tl);
        // Add five tabs.  Three have icons and two have text titles
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.baseline_add_black_48));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.baseline_bug_report_black_48));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.baseline_chat_black_48dp));
        tabLayout.addTab(tabLayout.newTab().setText("Send"));
        tabLayout.addTab(tabLayout.newTab().setText("Send & Post"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    LToast.showShort(getActivity(), "Tab " + tabLayout.getSelectedTabPosition());
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    LToast.showShort(getActivity(), "Tab " + tabLayout.getSelectedTabPosition());
                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    LToast.showShort(getActivity(), "Tab " + tabLayout.getSelectedTabPosition());
                } else if (tabLayout.getSelectedTabPosition() == 3) {
                    LToast.showShort(getActivity(), "Tab " + tabLayout.getSelectedTabPosition());
                } else if (tabLayout.getSelectedTabPosition() == 4) {
                    LToast.showShort(getActivity(), "Tab " + tabLayout.getSelectedTabPosition());
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
