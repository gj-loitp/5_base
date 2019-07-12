package vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayout

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LPopupMenu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.views.LAppBarLayout
import com.views.LToast
import loitp.basemaster.R

class CollapsingToolbarLayoutActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary))

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        /*CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.list_comic));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity, R.color.White));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(activity, R.color.White));*/

        val appBarLayout = findViewById<LAppBarLayout>(R.id.app_bar)
        appBarLayout.setOnStateChangeListener { toolbarChange ->
            //LLog.d(TAG, "toolbarChange: " + toolbarChange);
            if (toolbarChange == LAppBarLayout.State.COLLAPSED) {
                //COLLAPSED appBarLayout min
                LLog.d(TAG, "COLLAPSED toolbarChange: $toolbarChange")
            } else if (toolbarChange == LAppBarLayout.State.EXPANDED) {
                //EXPANDED appBarLayout max
                LLog.d(TAG, "EXPANDED toolbarChange: $toolbarChange")
            } else {
                //IDLE appBarLayout not min not max
                LLog.d(TAG, "IDLE toolbarChange: $toolbarChange")
            }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        findViewById<View>(R.id.bt_menu).setOnClickListener(this)
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
        when (v.id) {
            R.id.bt_menu -> LPopupMenu.show(activity, v, R.menu.menu_popup) { menuItem -> LToast.show(activity, menuItem.title.toString()) }
        }
    }
}
