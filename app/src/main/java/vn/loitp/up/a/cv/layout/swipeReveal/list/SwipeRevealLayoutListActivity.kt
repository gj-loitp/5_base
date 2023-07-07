package vn.loitp.up.a.cv.layout.swipeReveal.list

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.ASwipeRevealLayoutListBinding

@LogTag("SwipeRevealLayoutListActivity")
@IsFullScreen(false)
class SwipeRevealLayoutListActivity : BaseActivityFont() {
    private lateinit var binding: ASwipeRevealLayoutListBinding
    private var listAdapter: ListAdapter? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASwipeRevealLayoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.listView.adapter = listAdapter
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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            binding.toolbar.setNavigationOnClickListener {
                onBaseBackPressed()
            }
        }
    }
}
