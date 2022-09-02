package vn.loitp.app.activity.customviews.simpleRatingBar

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_simple_rating_bar.*
import vn.loitp.app.R

@LogTag("SimpleRatingBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class SimpleRatingBarActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_simple_rating_bar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        viewPager.adapter = SamplePagerAdapter(supportFragmentManager)
        tabLayout.addTab(tabLayout.newTab().setText("Animation Demo"))
        tabLayout.addTab(tabLayout.newTab().setText("RecyclerView Demo"))
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
    }

    inner class SamplePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {
        private val fragments: MutableList<Fragment>

        init {
            fragments = ArrayList()
            fragments.add(DemoFragment())
            fragments.add(ListFragment())
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

    }
}
