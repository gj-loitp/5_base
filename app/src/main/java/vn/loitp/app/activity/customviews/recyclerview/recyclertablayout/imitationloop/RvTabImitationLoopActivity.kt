package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.imitationloop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_tablayout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo

@LogTag("RvTabImitationLoopActivity")
@IsFullScreen(false)
open class RvTabImitationLoopActivity : BaseFontActivity(), ViewPager.OnPageChangeListener {

    private var mScrollState: Int = 0
    private lateinit var mAdapterTab: RvTabImitationLoopPagerAdapter
    private var mItems = ArrayList<String>()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout
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

        mItems.add(":)")
        for (i in 1..9) {
            mItems.add(i.toString())
        }

        mAdapterTab = RvTabImitationLoopPagerAdapter()
        mAdapterTab.addAll(mItems)

        viewPager.adapter = mAdapterTab
        viewPager.currentItem = mAdapterTab.getCenterPosition(0)
        viewPager.addOnPageChangeListener(this)

        recyclerTabLayout.setUpWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        //got to center
        val nearLeftEdge = position <= mItems.size
        val nearRightEdge = position >= mAdapterTab.count - mItems.size
        if (nearLeftEdge || nearRightEdge) {
            viewPager.setCurrentItem(mAdapterTab.getCenterPosition(0), false)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        mScrollState = state
    }

    companion object {

        protected const val KEY_DEMO = "demo"

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabImitationLoopActivity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}
