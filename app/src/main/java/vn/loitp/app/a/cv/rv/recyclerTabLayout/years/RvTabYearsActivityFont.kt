package vn.loitp.app.a.cv.rv.recyclerTabLayout.years

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_tab_layout.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.recyclerTabLayout.Demo
import java.util.* // ktlint-disable no-wildcard-imports

@LogTag("RvTabYearsActivity")
@IsFullScreen(false)
class RvTabYearsActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tab_layout
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

        val startYear = 1900
        val endYear = 3000
        val initialYear = Calendar.getInstance().get(Calendar.YEAR)

        val items = ArrayList<String>()
        for (i in startYear..endYear) {
            items.add(i.toString())
        }

        val demoYearsPagerAdapter = YearsPagerAdapter()
        demoYearsPagerAdapter.addAll(items)

        viewPager.adapter = demoYearsPagerAdapter
        viewPager.currentItem = initialYear - startYear

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
            val intent = Intent(context, RvTabYearsActivityFont::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}
