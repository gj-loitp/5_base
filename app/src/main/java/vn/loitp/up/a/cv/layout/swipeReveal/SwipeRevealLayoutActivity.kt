package vn.loitp.up.a.cv.layout.swipeReveal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.tranIn
import vn.loitp.R
import vn.loitp.databinding.ALayoutSwipeRevealBinding
import vn.loitp.up.a.cv.layout.swipeReveal.grid.SwipeRevealLayoutGridActivity
import vn.loitp.up.a.cv.layout.swipeReveal.list.SwipeRevealLayoutListActivity
import vn.loitp.up.a.cv.layout.swipeReveal.rv.SwipeRevealLayoutRecyclerActivity

// https://github.com/chthai64/SwipeRevealLayout
@LogTag("SwipeRevealLayoutActivity")
@IsFullScreen(false)
class SwipeRevealLayoutActivity : BaseActivityFont() {

    private lateinit var binding: ALayoutSwipeRevealBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutSwipeRevealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        binding.tv1.setSafeOnClickListener {
            moreOnClick()
        }
        binding.tv1Delete.setSafeOnClickListener {
            deleteOnClick()
        }
        binding.fl1.setSafeOnClickListener {
            layoutOneOnClick()
        }
        binding.tv2.setSafeOnClickListener {
            archiveOnClick()
        }
        binding.tv2Help.setSafeOnClickListener {
            helpOnClick()
        }
        binding.fl2.setSafeOnClickListener {
            layoutTwoOnClick()
        }
        binding.flSearch.setSafeOnClickListener {
            searchOnClick()
        }
        binding.fl3.setSafeOnClickListener {
            layoutThreeOnClick()
        }
        binding.flStart.setSafeOnClickListener {
            starOnClick()
        }
        binding.fl4.setSafeOnClickListener {
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
                this.tranIn()
                return true
            }
            R.id.actionListView -> {
                startActivity(Intent(this, SwipeRevealLayoutListActivity::class.java))
                this.tranIn()
                return true
            }
            R.id.actionGridView -> {
                startActivity(Intent(this, SwipeRevealLayoutGridActivity::class.java))
                this.tranIn()
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
