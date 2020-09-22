package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.years

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_tablayout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo
import java.util.*

@LayoutId(R.layout.activity_recycler_tablayout)
@LogTag("RvTabYearsActivity")
@IsFullScreen(false)
class RvTabYearsActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val demo = Demo.valueOf(intent.getStringExtra(KEY_DEMO))

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
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val KEY_DEMO = "demo"

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabYearsActivity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}