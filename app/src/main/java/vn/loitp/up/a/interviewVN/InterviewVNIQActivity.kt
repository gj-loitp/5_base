package vn.loitp.up.a.interviewVN

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseFragment
import com.loitp.core.base.OnBackPressedListener
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.addFragment
import vn.loitp.R
import vn.loitp.databinding.AInterviewVnIqBinding
import vn.loitp.up.a.demo.fragmentFlow.BaseFragmentFlow

@LogTag("InterviewVNIQActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class InterviewVNIQActivity : BaseActivityFont() {

    private lateinit var binding: AInterviewVnIqBinding
    var onBackClickListener: OnBackPressedListener? = null
    private var doubleBackToExitPressedOnce = false

    private fun getListener(): FragmentManager.OnBackStackChangedListener {
        return FragmentManager.OnBackStackChangedListener {
            // print("OnBackStackChangedListener")
            supportFragmentManager.let {
                val currFrag = it.findFragmentById(R.id.flContainer) as BaseFragmentFlow?
                currFrag?.onFragmentResume()
            }
        }
    }

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AInterviewVnIqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.addOnBackStackChangedListener(getListener())
        setupViews()
    }

    private fun setupViews() {
        addFrm(FrmListPackage())
    }

    override fun onBaseBackPressed() {
        if (onBackClickListener != null && onBackClickListener!!.onBackPressed()) {
            return
        }
        if (doubleBackToExitPressedOnce) {
            super.onBaseBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        showShortInformation(msg = getString(R.string.press_again_to_exit), isTopAnchor = false)
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    fun addFrm(baseFragment: BaseFragment) {
        addFragment(
            containerFrameLayoutIdRes = R.id.flContainer,
            fragment = baseFragment,
            tag = baseFragment.javaClass.simpleName,
            isAddToBackStack = true
        )
    }

//    private fun clearAllFragments() {
//        val frmFlow0 =
//            LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow0::class.java.simpleName)
//        frmFlow0?.let {
//            (it as BaseFragmentFlow).popThisFragment()
//        }
//        val frmFlow1 =
//            LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow1::class.java.simpleName)
//        frmFlow1?.let {
//            (it as BaseFragmentFlow).popThisFragment()
//        }
//        val frmFlow2 =
//            LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow2::class.java.simpleName)
//        frmFlow2?.let {
//            (it as BaseFragmentFlow).popThisFragment()
//        }
//    }

}
