package vn.loitp.app.activity.customviews.layout.swipereveallayout.list

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_swipe_reveal_layout_list.*
import vn.loitp.app.R
import java.util.*

@LogTag("SwipeRevealLayoutListActivity")
@IsFullScreen(false)
class SwipeRevealLayoutListActivity : BaseFontActivity() {

    private var listAdapter: ListAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipe_reveal_layout_list
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
