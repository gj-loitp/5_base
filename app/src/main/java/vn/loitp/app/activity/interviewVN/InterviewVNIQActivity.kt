package vn.loitp.app.activity.interviewVN

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentManager
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.base.BaseFragment
import com.loitpcore.core.base.OnBackPressedListener
import com.loitpcore.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_interview_vn_iq.*
import vn.loitp.app.R
import vn.loitp.app.activity.demo.fragmentFlow.BaseFragmentFlow

@LogTag("InterviewVNIQActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class InterviewVNIQActivity : BaseFontActivity() {

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

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_interview_vn_iq
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener(getListener())
        setupViews()
    }

    private fun setupViews() {
        addFragment(FrmListPackage())
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

    fun addFragment(baseFragment: BaseFragment) {
        LScreenUtil.addFragment(
            activity = this,
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
