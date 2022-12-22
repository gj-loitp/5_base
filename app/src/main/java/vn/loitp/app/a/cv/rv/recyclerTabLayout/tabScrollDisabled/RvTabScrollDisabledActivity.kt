package vn.loitp.app.a.cv.rv.recyclerTabLayout.tabScrollDisabled

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_tablayout_demo_tab_scroll_disabled.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.recyclerTabLayout.Demo
import vn.loitp.app.a.cv.rv.recyclerTabLayout.DemoColorPagerAdapter
import vn.loitp.app.a.cv.rv.recyclerTabLayout.utils.DemoData

@LogTag("RvTabScrollDisabledActivity")
@IsFullScreen(false)
class RvTabScrollDisabledActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_demo_tab_scroll_disabled
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val keyDemo = intent.getStringExtra(KEY_DEMO)
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

        val demoColorPagerAdapter = DemoColorPagerAdapter()
        demoColorPagerAdapter.addAll(items)
        viewPager.adapter = demoColorPagerAdapter

        recyclerTabLayout.setUpWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBaseBackPressed()
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
