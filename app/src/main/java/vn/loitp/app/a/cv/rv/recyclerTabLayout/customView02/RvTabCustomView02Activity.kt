package vn.loitp.app.a.cv.rv.recyclerTabLayout.customView02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.views.rv.recyclerTabLayout.RecyclerTabLayout
import vn.loitp.R
import vn.loitp.app.a.cv.rv.recyclerTabLayout.Demo
import vn.loitp.app.a.cv.rv.recyclerTabLayout.DemoImagePagerAdapter
import vn.loitp.app.a.cv.rv.recyclerTabLayout.utils.DemoData

@LogTag("RvTabCustomView02Activity")
@IsFullScreen(false)
class RvTabCustomView02Activity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_demo_custom_view02
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
            onBaseBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val KEY_DEMO = "demo"

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabCustomView02Activity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}
