package vn.loitp.app.activity.customviews.layout.motionlayout.viewpagerdemo

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager.widget.ViewPager
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.motion_16_viewpager.*
import loitp.basemaster.R

class MotionLayoutViewPagerActivity2 : BaseFontActivity() {
    override fun setTag(): String {
        return javaClass.simpleName;
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.motion_23_viewpager;
    }

    override fun setFullScreen(): Boolean {
        return false;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val motionLayout = findViewById<MotionLayout>(R.id.motionLayout)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addPage("Page 1", R.layout.motion_16_viewpager_page1)
        adapter.addPage("Page 2", R.layout.motion_16_viewpager_page2)
        adapter.addPage("Page 3", R.layout.motion_16_viewpager_page3)
        pager.adapter = adapter
        tabs.setupWithViewPager(pager)
        if (motionLayout != null) {
            pager.addOnPageChangeListener(motionLayout as ViewPager.OnPageChangeListener)
        }

        val doShowPaths = intent.getBooleanExtra("showPaths", false)
        motionLayout.setShowPaths(doShowPaths)
    }
}