package vn.loitp.app.activity.customviews.layout.swipeRevealLayout

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_swipe_reveal_layout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.swipeRevealLayout.grid.SwipeRevealLayoutGridActivity
import vn.loitp.app.activity.customviews.layout.swipeRevealLayout.list.SwipeRevealLayoutListActivity
import vn.loitp.app.activity.customviews.layout.swipeRevealLayout.recycler.SwipeRevealLayoutRecyclerActivity

// https://github.com/chthai64/SwipeRevealLayout
@LogTag("SwipeRevealLayoutActivity")
@IsFullScreen(false)
class SwipeRevealLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipe_reveal_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)
        tv1.setSafeOnClickListener {
            moreOnClick()
        }
        tv1Delete.setSafeOnClickListener {
            deleteOnClick()
        }
        fl1.setSafeOnClickListener {
            layoutOneOnClick()
        }
        tv2.setSafeOnClickListener {
            archiveOnClick()
        }
        tv2Help.setSafeOnClickListener {
            helpOnClick()
        }
        fl2.setSafeOnClickListener {
            layoutTwoOnClick()
        }
        flSearch.setSafeOnClickListener {
            searchOnClick()
        }
        fl3.setSafeOnClickListener {
            layoutThreeOnClick()
        }
        flStart.setSafeOnClickListener {
            starOnClick()
        }
        fl4.setSafeOnClickListener {
            layoutFourOnClick()
        }
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

    private fun layoutOneOnClick() {
        showShortInformation("Layout 1 clicked", true)
    }

    private fun layoutTwoOnClick() {
        showShortInformation("Layout 2 clicked", true)
    }

    private fun layoutThreeOnClick() {
        showShortInformation("Layout 3 clicked", true)
    }

    private fun layoutFourOnClick() {
        showShortInformation("Layout 4 clicked", true)
    }

    private fun moreOnClick() {
        showShortInformation("More clicked", true)
    }

    private fun deleteOnClick() {
        showShortInformation("Delete clicked", true)
    }

    private fun archiveOnClick() {
        showShortInformation("Archive clicked", true)
    }

    private fun helpOnClick() {
        showShortInformation("Help clicked", true)
    }

    private fun searchOnClick() {
        showShortInformation("Search clicked", true)
    }

    private fun starOnClick() {
        showShortInformation("Star clicked", true)
    }
}
