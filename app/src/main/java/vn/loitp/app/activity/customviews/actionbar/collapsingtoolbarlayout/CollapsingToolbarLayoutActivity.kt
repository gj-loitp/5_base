package vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayout

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LPopupMenu
import com.google.android.material.snackbar.Snackbar
import com.views.LAppBarLayout
import com.views.LToast
import kotlinx.android.synthetic.main.activity_collapsingtoolbar.*
import vn.loitp.app.R

class CollapsingToolbarLayoutActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary))

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        appBarLayout.setOnStateChangeListener(object : LAppBarLayout.OnStateChangeListener {
            override fun onStateChange(toolbarChange: LAppBarLayout.State) {
                when (toolbarChange) {
                    LAppBarLayout.State.COLLAPSED -> //COLLAPSED appBarLayout min
                        LLog.d(TAG, "COLLAPSED toolbarChange: $toolbarChange")
                    LAppBarLayout.State.EXPANDED -> //EXPANDED appBarLayout max
                        LLog.d(TAG, "EXPANDED toolbarChange: $toolbarChange")
                    else -> //IDLE appBarLayout not min not max
                        LLog.d(TAG, "IDLE toolbarChange: $toolbarChange")
                }
            }
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        btMenu.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_collapsingtoolbar
    }

    override fun onClick(v: View) {
        when (v) {
            btMenu -> LPopupMenu.show(activity, v, R.menu.menu_popup, object : LPopupMenu.CallBack {
                override fun clickOnItem(menuItem: MenuItem) {
                    LToast.show(activity, menuItem.title.toString())
                }
            })
        }
    }
}
