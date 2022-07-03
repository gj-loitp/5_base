package vn.loitp.app.activity.customviews.menu.resideMenu

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import com.loitpcore.views.menu.resideMenu.ResideMenu
import kotlinx.android.synthetic.main.frm_reside_menu_home.*
import vn.loitp.app.R

@LogTag("HomeFragment")
class HomeFragment : BaseFragment() {

    private var resideMenu: ResideMenu? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_reside_menu_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    private fun setUpViews() {
        val parentActivity = activity as ResideMenuActivity
        resideMenu = parentActivity.resideMenu

        btOpenMenu.setOnClickListener { view: View? ->
            resideMenu?.openMenu(ResideMenu.DIRECTION_LEFT)
        }

        resideMenu?.addIgnoredView(layoutIgnoredView)
    }
}
