package vn.loitp.up.a.cv.layout.swipeReveal.rv

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.ARecyclerBinding

@LogTag("SwipeRevealLayoutRecyclerActivity")
@IsFullScreen(false)
class SwipeRevealLayoutRecyclerActivity : BaseActivityFont() {
    private lateinit var binding: ARecyclerBinding
    private var recyclerAdapter: RecyclerAdapter? = null

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = RecyclerAdapter(this, createList())
        binding.recyclerView.adapter = recyclerAdapter
    }

    private fun createList(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0 until 20) {
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
