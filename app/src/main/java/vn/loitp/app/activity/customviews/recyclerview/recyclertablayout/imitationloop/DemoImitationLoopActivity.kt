package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.imitationloop

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

open class DemoImitationLoopActivity : BaseFontActivity(), ViewPager.OnPageChangeListener {

    private var mScrollState: Int = 0
    private lateinit var mAdapter: DemoImitationLoopPagerAdapter
    private lateinit var mViewPager: ViewPager
    private var mItems = ArrayList<String>()


    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recyler_tablayout_demo_basic
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val demo = Demo.valueOf(intent.getStringExtra(KEY_DEMO))

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(demo.titleResId)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mItems.add(":)")
        for (i in 1..9) {
            mItems.add(i.toString())
        }

        mAdapter = DemoImitationLoopPagerAdapter()
        mAdapter.addAll(mItems)

        mViewPager = findViewById(R.id.view_pager)
        mViewPager.adapter = mAdapter
        mViewPager.currentItem = mAdapter.getCenterPosition(0)
        mViewPager.addOnPageChangeListener(this)

        val recyclerTabLayout = findViewById<RecyclerTabLayout>(R.id.recycler_tab_layout)
        recyclerTabLayout.setUpWithViewPager(mViewPager)
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
        val nearRightEdge = position >= mAdapter.count - mItems.size
        if (nearLeftEdge || nearRightEdge) {
            mViewPager.setCurrentItem(mAdapter.getCenterPosition(0), false)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        mScrollState = state
    }

    companion object {

        protected const val KEY_DEMO = "demo"

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, DemoImitationLoopActivity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}