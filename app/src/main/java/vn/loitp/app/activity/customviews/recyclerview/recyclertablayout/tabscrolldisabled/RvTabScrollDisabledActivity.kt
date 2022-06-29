package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.tabscrolldisabled

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_tablayout_demo_tab_scroll_disabled.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoColorPagerAdapter
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.utils.DemoData

@LogTag("RvTabScrollDisabledActivity")
@IsFullScreen(false)
class RvTabScrollDisabledActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_demo_tab_scroll_disabled
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val keyDemo = intent.getStringExtra(KEY_DEMO)
        if (keyDemo.isNullOrEmpty()) {
            onBackPressed()
            return
        }
        val demo = Demo.valueOf(keyDemo)

        toolbar.setTitle(demo.titleResId)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items = DemoData.loadDemoColorItems(this)
        items.sortedWith { lhs, rhs -> lhs.name.compareTo(rhs.name) }

        val demoColorPagerAdapter = DemoColorPagerAdapter()
        demoColorPagerAdapter.addAll(items)
        viewPager.adapter = demoColorPagerAdapter

        recyclerTabLayout.setUpWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val KEY_DEMO = "demo"

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabScrollDisabledActivity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}
