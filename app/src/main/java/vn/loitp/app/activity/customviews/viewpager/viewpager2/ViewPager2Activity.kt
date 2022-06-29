package vn.loitp.app.activity.customviews.viewpager.viewpager2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_view_pager_2.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.viewpager.viewpager2.FrmViewPager2.Companion.getInstance

class ViewPager2Activity : AppCompatActivity() {

    companion object {
        val tabName = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_2)

        setupViews()
    }

    private fun setupViews() {
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        setUpViewPager()

        btSwitchOrientation.setSafeOnClickListener {
            if (viewPager.orientation == ViewPager2.ORIENTATION_VERTICAL) {
                viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            } else {
                viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
            }
        }
    }

    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
        TabLayoutMediator(tabs, viewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabName[position]
        }.attach()
    }

    private inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun createFragment(position: Int): Fragment {
            return getInstance(position + 1)
        }

        override fun getItemCount(): Int {
            return tabName.size
        }
    }
}
