package vn.loitp.up.a.u

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.FONT_PATH
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.changeTabsFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.utils.*
import vn.loitp.R
import vn.loitp.a.u.FrmUtils
import vn.loitp.databinding.AUtilsBinding

@LogTag("UtilsActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class UtilsActivity : BaseActivityFont() {
    private lateinit var binding: AUtilsBinding
    private val listClass = ArrayList<String>()

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AUtilsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupData()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = UtilsActivity::class.java.simpleName
        }
        binding.viewPager.adapter = SlidePagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.changeTabsFont(FONT_PATH)
    }

    private fun setupData() {

        listClass.add(EncryptUtils::class.java.simpleName)
        listClass.add(DeviceUtils::class.java.simpleName)
        listClass.add(ConvertUtils::class.java.simpleName)
        listClass.add(CloseUtils::class.java.simpleName)
        listClass.add("ClipboardUtils")
        listClass.add("CleanUtils")
        listClass.add(BarUtils::class.java.simpleName)
        listClass.add(AppUtils::class.java.simpleName)
        listClass.add("ActivityUtils")

        binding.viewPager.adapter?.notifyDataSetChanged()
    }

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
