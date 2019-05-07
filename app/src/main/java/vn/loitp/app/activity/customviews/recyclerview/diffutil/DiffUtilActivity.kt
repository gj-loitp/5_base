package com.antonioleiva.diffutilkotlin

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

    private val items = (1..10).map { Content(it, "Item $it", "http://lorempixel.com/200/200/animals/$it/") }

    fun generate(): List<Content> {
        val rand = Random(System.currentTimeMillis())
        return items.filter { rand.nextBoolean() }
    }

    val adapter = ContentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_util)
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter
        adapter.items = items
        bt.setOnClickListener {
            adapter.items = generate()
        }
    }
}
