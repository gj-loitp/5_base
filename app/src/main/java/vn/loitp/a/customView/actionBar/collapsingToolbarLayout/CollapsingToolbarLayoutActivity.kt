package vn.loitp.a.customView.actionBar.collapsingToolbarLayout

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LPopupMenu
import com.loitp.views.layout.appBar.LAppBarLayout
import kotlinx.android.synthetic.main.activity_collapsing_toolbar_layout_with_tab_layout.*
import vn.loitp.R

@LogTag("CollapsingToolbarLayoutActivity")
@IsFullScreen(false)
class CollapsingToolbarLayoutActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_collapsing_toolbar_layout_with_tab_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        setCustomStatusBar(
            colorStatusBar = Color.TRANSPARENT,
            colorNavigationBar = LAppResource.getColor(R.color.colorPrimary)
        )

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener {
            onBaseBackPressed()
        }

        appBarLayout.setOnStateChangeListener(object : LAppBarLayout.OnStateChangeListener {
            override fun onStateChange(toolbarChange: LAppBarLayout.State) {
                when (toolbarChange) {
                    LAppBarLayout.State.COLLAPSED -> // COLLAPSED appBarLayout min
                        logD("COLLAPSED toolbarChange: $toolbarChange")
                    LAppBarLayout.State.EXPANDED -> // EXPANDED appBarLayout max
                        logD("EXPANDED toolbarChange: $toolbarChange")
                    else -> // IDLE appBarLayout not min not max
                        logD("IDLE toolbarChange: $toolbarChange")
                }
            }
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        btMenu.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btMenu -> LPopupMenu.show(
                activity = this,
                showOnView = v,
                menuRes = R.menu.menu_popup,
                callback = { menuItem ->
                    showShortInformation(menuItem.title.toString())
                }
            )
        }
    }
}
