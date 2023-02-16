package vn.loitp.up.a.cv.layout.swipeReveal.grid

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.ASwipeRevealLayoutGridBinding

@LogTag("SwipeRevealLayoutGridActivity")
@IsFullScreen(false)
class SwipeRevealLayoutGridActivity : BaseActivityFont() {
    private lateinit var binding: ASwipeRevealLayoutGridBinding
    private var gridAdapter: GridAdapter? = null

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASwipeRevealLayoutGridBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.gridView.adapter = gridAdapter
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
