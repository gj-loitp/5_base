package vn.loitp.app.activity.customviews.layout.swipereveallayout.grid

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_swipe_reveal_layout_grid.*
import vn.loitp.app.R
import java.util.*

@LogTag("SwipeRevealLayoutGridActivity")
@IsFullScreen(false)
class SwipeRevealLayoutGridActivity : BaseFontActivity() {

    private var gridAdapter: GridAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipe_reveal_layout_grid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionBar()
        setupGrid()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Only if you need to restore open/close state when
        // the orientation is changed
        gridAdapter?.saveStates(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Only if you need to restore open/close state when
        // the orientation is changed
        gridAdapter?.restoreStates(savedInstanceState)
    }

    private fun setupGrid() {
        gridAdapter = GridAdapter(this, createList(20))
        gridView.adapter = gridAdapter
    }

    private fun createList(n: Int): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0 until n) {
            list.add("View $i")
        }
        return list
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }
}
