package vn.loitp.a.cv.layout.swipeReveal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.a_layout_swipe_reveal.*
import vn.loitp.R
import vn.loitp.a.cv.layout.swipeReveal.grid.SwipeRevealLayoutGridActivityFont
import vn.loitp.a.cv.layout.swipeReveal.list.SwipeRevealLayoutListActivityFont
import vn.loitp.a.cv.layout.swipeReveal.rv.SwipeRevealLayoutRecyclerActivityFont

// https://github.com/chthai64/SwipeRevealLayout
@LogTag("SwipeRevealLayoutActivity")
@IsFullScreen(false)
class SwipeRevealLayoutActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_swipe_reveal
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
                startActivity(Intent(this, SwipeRevealLayoutRecyclerActivityFont::class.java))
                LActivityUtil.tranIn(this)
                return true
            }
            R.id.actionListView -> {
                startActivity(Intent(this, SwipeRevealLayoutListActivityFont::class.java))
                LActivityUtil.tranIn(this)
                return true
            }
            R.id.actionGridView -> {
                startActivity(Intent(this, SwipeRevealLayoutGridActivityFont::class.java))
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
