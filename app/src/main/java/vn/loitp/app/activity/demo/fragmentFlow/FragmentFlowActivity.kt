package vn.loitp.app.activity.demo.fragmentFlow

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.base.BaseFragment
import com.loitp.core.base.OnBackPressedListener
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_demo_fragment_flow.*
import vn.loitp.R

@LogTag("FragmentFlowActivity")
@IsFullScreen(false)
class FragmentFlowActivity : BaseFontActivity() {

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
        return R.layout.activity_demo_fragment_flow
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener(getListener())

        setupViews()
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
            this.tvTitle?.text = FragmentFlowActivity::class.java.simpleName
        }
        btAddFrmFlow0.setSafeOnClickListener {
            showFragment(FrmFlow0())
        }
        btClearAllFrm.setSafeOnClickListener {
            clearAllFragments()
        }
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

    fun showFragment(baseFragment: BaseFragment) {
        LScreenUtil.addFragment(
            activity = this,
            containerFrameLayoutIdRes = R.id.flContainer,
            fragment = baseFragment,
            tag = baseFragment.javaClass.simpleName,
            isAddToBackStack = true
        )
    }

    private fun clearAllFragments() {
        val frmFlow0 =
            LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow0::class.java.simpleName)
        frmFlow0?.let {
            (it as BaseFragmentFlow).popThisFragment()
        }
        val frmFlow1 =
            LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow1::class.java.simpleName)
        frmFlow1?.let {
            (it as BaseFragmentFlow).popThisFragment()
        }
        val frmFlow2 =
            LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow2::class.java.simpleName)
        frmFlow2?.let {
            (it as BaseFragmentFlow).popThisFragment()
        }
    }

    @SuppressLint("SetTextI18n")
    fun print(msg: String) {
        tvInformation.text = tvInformation.text.toString() + "\n" + msg
    }
}
