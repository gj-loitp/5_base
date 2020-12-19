package vn.loitp.app.activity.customviews.layout.swipereveallayout

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_swipe_reveal_layout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.swipereveallayout.grid.SwipeRevealLayoutGridActivity
import vn.loitp.app.activity.customviews.layout.swipereveallayout.list.SwipeRevealLayoutListActivity
import vn.loitp.app.activity.customviews.layout.swipereveallayout.recycler.SwipeRevealLayoutRecyclerActivity

//https://github.com/chthai64/SwipeRevealLayout
@LogTag("SwipeRevealLayoutActivity")
@IsFullScreen(false)
class SwipeRevealLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipe_reveal_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_swipe_reveal_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionRecyclerView -> {
                startActivity(Intent(this, SwipeRevealLayoutRecyclerActivity::class.java))
                LActivityUtil.tranIn(this)
                return true
            }
            R.id.actionListView -> {
                startActivity(Intent(this, SwipeRevealLayoutListActivity::class.java))
                LActivityUtil.tranIn(this)
                return true
            }
            R.id.actionGridView -> {
                startActivity(Intent(this, SwipeRevealLayoutGridActivity::class.java))
                LActivityUtil.tranIn(this)
                return true
            }
        }
        return false
    }

    fun layoutOneOnClick() {
        showShortInformation("Layout 1 clicked", true)
    }

    fun layoutTwoOnClick() {
        showShortInformation("Layout 2 clicked", true)
    }

    fun layoutThreeOnClick() {
        showShortInformation("Layout 3 clicked", true)
    }

    fun layoutFourOnClick() {
        showShortInformation("Layout 4 clicked", true)
    }

    fun moreOnClick() {
        showShortInformation("More clicked", true)
    }

    fun deleteOnClick() {
        showShortInformation("Delete clicked", true)
    }

    fun archiveOnClick() {
        showShortInformation("Archive clicked", true)
    }

    fun helpOnClick() {
        showShortInformation("Help clicked", true)
    }

    fun searchOnClick() {
        showShortInformation("Search clicked", true)
    }

    fun starOnClick() {
        showShortInformation("Star clicked", true)
    }
}
