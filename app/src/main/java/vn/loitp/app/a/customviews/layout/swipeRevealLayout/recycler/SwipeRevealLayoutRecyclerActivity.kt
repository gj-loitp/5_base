package vn.loitp.app.a.customviews.layout.swipeRevealLayout.recycler

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_recycler.*
import vn.loitp.R

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
        recyclerAdapter = RecyclerAdapter(this, createList())
        recyclerView.adapter = recyclerAdapter
    }

    private fun createList(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0 until 20) {
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
