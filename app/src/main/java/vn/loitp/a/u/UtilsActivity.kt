package vn.loitp.a.u

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.changeTabsFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.utils.*
import kotlinx.android.synthetic.main.a_utils.*
import vn.loitp.R

@LogTag("UtilsActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class UtilsActivity : BaseActivityFont() {

    private val listClass = ArrayList<String>()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_utils
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupData()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = UtilsActivity::class.java.simpleName
        }
        viewPager.adapter = SlidePagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.changeTabsFont(Constants.FONT_PATH)
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

    //TODO use view pager 2
    private inner class SlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            val frm = FrmUtils()
            val bundle = Bundle().apply {
                putString(FrmUtils.KEY_CLASS, listClass[position])
            }
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
