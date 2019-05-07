package com.antonioleiva.diffutilkotlin

import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_diff_util.*
import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity

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

    val provider = ContentProvider()
    val adapter = ContentAdapter()
    val handler = Handler()
    val runnable = { fillAdapter(); scheduleReload() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_util)

        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter
        runnable()
    }

    private fun scheduleReload() {
        handler.postDelayed(runnable, 3000)
    }

    private fun fillAdapter() {
        adapter.items = provider.generate()
    }
}
