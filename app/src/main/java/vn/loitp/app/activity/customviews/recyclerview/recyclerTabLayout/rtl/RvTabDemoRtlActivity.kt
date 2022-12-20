package vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.rtl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_tablayout_demo_rtl.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.Demo
import vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.DemoColorPagerAdapter
import vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.basic.RvTabDemoBasicActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.utils.DemoData

@LogTag("RvTabDemoRtlActivity")
@IsFullScreen(false)
class RvTabDemoRtlActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_demo_rtl
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val keyDemo = intent.getStringExtra(RvTabDemoBasicActivity.KEY_DEMO)
        if (keyDemo.isNullOrEmpty()) {
            onBaseBackPressed()
            return
        }
        val demo = Demo.valueOf(keyDemo)

        toolbar.setTitle(demo.titleResId)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items = DemoData.loadDemoColorItems(this)
        items.sortedWith { lhs, rhs -> lhs.name.compareTo(rhs.name) }

        val adapter = DemoColorPagerAdapter()
        adapter.addAll(items)
        viewPager.adapter = adapter

        recyclerTabLayout.setUpWithViewPager(viewPager)
    }

    companion object {

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabDemoRtlActivity::class.java)
            intent.putExtra(RvTabDemoBasicActivity.KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}
