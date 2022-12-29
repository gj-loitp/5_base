package vn.loitp.a.cv.layout.swipeReveal.list

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.a_swipe_reveal_layout_list.*
import vn.loitp.R

@LogTag("SwipeRevealLayoutListActivity")
@IsFullScreen(false)
class SwipeRevealLayoutListActivity : BaseFontActivity() {

    private var listAdapter: ListAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.a_swipe_reveal_layout_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionBar()
        setupList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        listAdapter?.saveStates(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        listAdapter?.restoreStates(savedInstanceState)
    }

    private fun setupList() {
        listAdapter = ListAdapter(this, createList(20))
        listView.adapter = listAdapter
    }

    @Suppress("unused")
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
                onBaseBackPressed()
            }
        }
    }
}
