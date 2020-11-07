package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import android.os.Bundle
import android.view.View
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.frm_coordinator_1.*
import vn.loitp.app.R

@LogTag("RelativePopupWindowActivity")
class FrmCoordinator1 : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coordinator_1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        tl.addTab(tl.newTab().setIcon(R.drawable.ic_add_black_48dp))
        tl.addTab(tl.newTab().setIcon(R.drawable.ic_bug_report_black_48dp))
        tl.addTab(tl.newTab().setIcon(R.drawable.ic_chat_black_48dp))
        tl.addTab(tl.newTab().setText("Send"))
        tl.addTab(tl.newTab().setText("Send & Post"))

        tl.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tl.selectedTabPosition) {
                    0 -> {
                        showShortInformation("Tab " + tl.selectedTabPosition, true)
                    }
                    1 -> {
                        showShortInformation("Tab " + tl.selectedTabPosition, true)
                    }
                    2 -> {
                        showShortInformation("Tab " + tl.selectedTabPosition, true)
                    }
                    3 -> {
                        showShortInformation("Tab " + tl.selectedTabPosition, true)
                    }
                    4 -> {
                        showShortInformation("Tab " + tl.selectedTabPosition, true)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}
