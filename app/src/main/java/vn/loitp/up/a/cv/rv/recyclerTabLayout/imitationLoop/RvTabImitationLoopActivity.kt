package vn.loitp.up.a.cv.rv.recyclerTabLayout.imitationLoop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.tranIn
import vn.loitp.databinding.ARecyclerTabLayoutBinding
import vn.loitp.up.a.cv.rv.recyclerTabLayout.Demo

@LogTag("RvTabImitationLoopActivity")
@IsFullScreen(false)
open class RvTabImitationLoopActivity : BaseActivityFont(), ViewPager.OnPageChangeListener {
    private lateinit var binding: ARecyclerTabLayoutBinding

    private var mScrollState: Int = 0
    private lateinit var mAdapterTab: RvTabImitationLoopPagerAdapter
    private var mItems = ArrayList<String>()

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARecyclerTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val keyDemo = intent.getStringExtra(KEY_DEMO)
        if (keyDemo.isNullOrEmpty()) {
            onBaseBackPressed()
            return
        }
        val demo = Demo.valueOf(keyDemo)

        binding.toolbar.setTitle(demo.titleResId)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mItems.add(":)")
        for (i in 1..9) {
            mItems.add(i.toString())
        }

        mAdapterTab = RvTabImitationLoopPagerAdapter()
        mAdapterTab.addAll(mItems)

        binding.viewPager.adapter = mAdapterTab
        binding.viewPager.currentItem = mAdapterTab.getCenterPosition(0)
        binding.viewPager.addOnPageChangeListener(this)

        binding.recyclerTabLayout.setUpWithViewPager(binding.viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBaseBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        // got to center
        val nearLeftEdge = position <= mItems.size
        val nearRightEdge = position >= mAdapterTab.count - mItems.size
        if (nearLeftEdge || nearRightEdge) {
            binding.viewPager.setCurrentItem(mAdapterTab.getCenterPosition(0), false)
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
            context.tranIn()
        }
    }
}
