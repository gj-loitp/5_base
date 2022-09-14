package vn.loitp.app.activity.utils

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.utils.util.* // ktlint-disable no-wildcard-imports
import kotlinx.android.synthetic.main.activity_utils.*
import vn.loitp.app.R

@LogTag("UtilsActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class UtilsActivity : BaseFontActivity() {

    private val listClass = ArrayList<String>()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_utils
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupData()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = UtilsActivity::class.java.simpleName
        }
        viewPager.adapter = SlidePagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        LUIUtil.changeTabsFont(tabLayout, Constants.FONT_PATH)
    }

    private fun setupData() {

        listClass.add(EncryptUtils::class.java.simpleName)
        listClass.add(DeviceUtils::class.java.simpleName)
        listClass.add(ConvertUtils::class.java.simpleName)
        listClass.add(CloseUtils::class.java.simpleName)
        listClass.add(ClipboardUtils::class.java.simpleName)
        listClass.add(CleanUtils::class.java.simpleName)
        listClass.add(BarUtils::class.java.simpleName)
        listClass.add(AppUtils::class.java.simpleName)
        listClass.add(ActivityUtils::class.java.simpleName)

        viewPager.adapter?.notifyDataSetChanged()
    }

    private inner class SlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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

        override fun getPageTitle(position: Int): CharSequence {
            return listClass[position]
        }
    }
}
