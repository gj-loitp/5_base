package vn.loitp.app.activity.customviews.menu.residemenu

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.views.menu.residemenu.ResideMenu
import kotlinx.android.synthetic.main.reside_menu_home.*
import vn.loitp.app.R

@LogTag("HomeFragment")
class HomeFragment : BaseFragment() {

    private var resideMenu: ResideMenu? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.reside_menu_home
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
