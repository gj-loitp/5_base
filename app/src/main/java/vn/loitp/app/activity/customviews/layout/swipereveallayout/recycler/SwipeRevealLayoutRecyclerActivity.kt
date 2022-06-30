package vn.loitp.app.activity.customviews.layout.swipereveallayout.recycler

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_recycler.*
import vn.loitp.app.R

@LogTag("SwipeRevealLayoutRecyclerActivity")
@IsFullScreen(false)
class SwipeRevealLayoutRecyclerActivity : BaseFontActivity() {

    private var recyclerAdapter: RecyclerAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler
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
        recyclerAdapter?.saveStates(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        recyclerAdapter?.restoreStates(savedInstanceState)
    }

    private fun setupList() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = RecyclerAdapter(this, createList(20))
        recyclerView.adapter = recyclerAdapter
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
