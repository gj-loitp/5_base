package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem

import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.views.recyclerview.recyclertablayout.RecyclerTabLayout

import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoImagePagerAdapter
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.utils.DemoData

class RvTabCustomView02Activity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_demo_custom_view02
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val demo = Demo.valueOf(intent.getStringExtra(KEY_DEMO))

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(demo.titleResId)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = DemoImagePagerAdapter()
        adapter.addAll(DemoData.loadImageResourceList())

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter

        val recyclerTabLayout = findViewById<RecyclerTabLayout>(R.id.recycler_tab_layout)
        recyclerTabLayout.setUpWithAdapter(RvTabCustomView02Adapter(viewPager))
        recyclerTabLayout.setPositionThreshold(0.5f)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val KEY_DEMO = "demo"

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabCustomView02Activity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}