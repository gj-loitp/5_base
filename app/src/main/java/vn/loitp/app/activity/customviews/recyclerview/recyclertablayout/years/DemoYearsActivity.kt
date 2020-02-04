package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.years

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
import java.util.*

class DemoYearsActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val demo = Demo.valueOf(intent.getStringExtra(KEY_DEMO))

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
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

        val adapter = DemoYearsPagerAdapter()
        adapter.addAll(items)

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = adapter
        viewPager.currentItem = initialYear - startYear

        val recyclerTabLayout = findViewById<RecyclerTabLayout>(R.id.recycler_tab_layout)
        recyclerTabLayout.setUpWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recyler_tablayout_demo_basic
    }

    companion object {

        private val KEY_DEMO = "demo"

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, DemoYearsActivity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}