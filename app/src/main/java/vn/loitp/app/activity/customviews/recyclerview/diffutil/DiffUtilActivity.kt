package vn.loitp.app.activity.customviews.recyclerview.diffutil

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_diff_util.*
import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity
import java.util.*

class DiffUtilActivity : BaseFontActivity() {
    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return "TAG" + javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_diff_util
    }

    private var items: MutableList<Content> = mutableListOf()

    fun generate(): List<Content> {
        val rand = Random(System.currentTimeMillis())
        return items.filter { rand.nextBoolean() }
    }

    //op1
    fun add(): List<Content> {
        items.add(Content(1, "Loitp ${System.currentTimeMillis()}", "http://lorempixel.com/200/200/animals/1/"))
        adapter.notifyDataSetChanged()
        return items
    }

    val adapter = ContentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_util)
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter
        for (i in 1..10) {
            items.add(Content(i, "Item $i", "http://lorempixel.com/200/200/animals/$i/"))
        }
        adapter.items = items
        bt.setOnClickListener {
            adapter.items = generate()
        }
        btAdd.setOnClickListener {
            adapter.items = add()
        }
    }
}
