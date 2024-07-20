package vn.loitp.up.a.cv.vp.vp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AVp2Binding
import vn.loitp.up.a.cv.vp.vp2.FrmViewPager2.Companion.getInstance

class ViewPager2Activity : BaseActivityFont() {

    companion object {
        val tabName = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
    }

    private lateinit var binding: AVp2Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ViewPager2Activity::class.java.simpleName
        }
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        setUpViewPager()

        binding.btSwitchOrientation.setSafeOnClickListener {
            if (binding.viewPager.orientation == ViewPager2.ORIENTATION_VERTICAL) {
                binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            } else {
                binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
            }
        }
    }

    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(
            /* tabLayout = */ binding.tabs,
            /* viewPager = */binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
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
