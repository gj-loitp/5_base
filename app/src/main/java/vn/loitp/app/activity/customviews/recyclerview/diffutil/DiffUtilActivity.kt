package vn.loitp.app.activity.customviews.recyclerview.diffutil

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.core.base.BaseFontActivity
import com.core.common.Constants
import kotlinx.android.synthetic.main.activity_recycler_view_diff_util.*
import vn.loitp.app.R
import java.util.*

class DiffUtilActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_diff_util
    }

    private var items: MutableList<Content> = mutableListOf()

    private fun generate(): List<Content> {
        val rand = Random(System.currentTimeMillis())
        return items.filter { rand.nextBoolean() }
    }

    fun add(): List<Content> {
        items.add(Content(1, "Loitp ${System.currentTimeMillis()}", Constants.URL_IMG_ANDROID))
        adapter.notifyDataSetChanged()
        return items
    }

    private fun shuffle(): List<Content> {
        items.shuffle()
        return items
    }

    val adapter = ContentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter
        for (i in 1..10) {
            items.add(Content(i, "Item $i", Constants.URL_IMG_ANDROID))
        }
        adapter.items = items
        bt.setOnClickListener {
            adapter.items = generate()
        }
        btAdd.setOnClickListener {
            adapter.items = add()
        }
        btShuffle.setOnClickListener {
            adapter.items = shuffle()
        }
    }
}
