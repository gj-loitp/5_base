package vn.loitp.app.activity.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LUIUtil
import com.utils.util.ActivityUtils
import com.utils.util.AppUtils
import com.utils.util.BarUtils
import kotlinx.android.synthetic.main.activity_utils.*
import vn.loitp.app.R

class UtilsActivity : BaseFontActivity() {

    private val listClass = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager.adapter = SlidePagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        LUIUtil.changeTabsFont(tabLayout, Constants.FONT_PATH)

        setupData()
    }

    private fun setupData() {

        listClass.add(BarUtils::class.java.simpleName)
        listClass.add(AppUtils::class.java.simpleName)
        listClass.add(ActivityUtils::class.java.simpleName)

        viewPager.adapter?.notifyDataSetChanged()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_utils
    }

    private inner class SlidePagerAdapter internal constructor(fm: FragmentManager)
        : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            val frm = FrmUtils()
            val bundle = Bundle()
            bundle.putString(FrmUtils.KEY_CLASS, listClass[position])
            frm.arguments = bundle
            return frm
        }

        override fun getCount(): Int {
            return listClass.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return listClass[position]
        }
    }
}
