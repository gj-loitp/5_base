package vn.loitp.app.activity.customviews.layout.coordinatorlayout;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.views.LToast;

import vn.loitp.app.R;

public class FrmCoordinator1 extends BaseFragment {
    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_coordinator_1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tl);
        // Add five tabs.  Three have icons and two have text titles
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.l_baseline_add_black_48));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.l_baseline_bug_report_black_48));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.l_baseline_chat_black_48dp));
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

    @org.jetbrains.annotations.Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }
}
