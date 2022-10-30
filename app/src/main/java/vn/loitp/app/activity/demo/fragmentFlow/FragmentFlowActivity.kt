package vn.loitp.app.activity.demo.fragmentFlow

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.base.BaseFragment
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_demo_fragment_flow.*
import vn.loitp.app.R

@LogTag("FragmentFlowActivity")
@IsFullScreen(false)
class FragmentFlowActivity : BaseFontActivity() {

    var onBackClickListener: OnBackClickListener? = null
    private var doubleBackToExitPressedOnce = false

    private fun getListener(): FragmentManager.OnBackStackChangedListener {
        return FragmentManager.OnBackStackChangedListener {
            // print("OnBackStackChangedListener")
            supportFragmentManager.let {
                val currFrag = it.findFragmentById(R.id.flContainer) as FrmFlowBase?
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
            this.viewShadow?.isVisible = true
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
//        super.onBaseBackPressed()
        if (onBackClickListener != null && onBackClickListener!!.onBackClick()) {
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
//    override fun onBackPressed() {
//        if (onBackClickListener != null && onBackClickListener!!.onBackClick()) {
//            return
//        }
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed()
//            return
//        }
//        this.doubleBackToExitPressedOnce = true
//        showShortInformation(msg = getString(R.string.press_again_to_exit), isTopAnchor = false)
//        Handler(Looper.getMainLooper()).postDelayed({
//            doubleBackToExitPressedOnce = false
//        }, 2000)
//    }

    fun showFragment(baseFragment: BaseFragment) {
        LScreenUtil.addFragment(
            this,
            R.id.flContainer,
            baseFragment,
            baseFragment.javaClass.simpleName,
            true
        )
    }

    private fun clearAllFragments() {
        val frmFlow0 =
            LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow0::class.java.simpleName)
        frmFlow0?.let {
            (it as FrmFlowBase).popThisFragment()
        }
        val frmFlow1 =
            LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow1::class.java.simpleName)
        frmFlow1?.let {
            (it as FrmFlowBase).popThisFragment()
        }
        val frmFlow2 =
            LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow2::class.java.simpleName)
        frmFlow2?.let {
            (it as FrmFlowBase).popThisFragment()
        }
    }

    @SuppressLint("SetTextI18n")
    fun print(msg: String) {
        tvInformation.text = tvInformation.text.toString() + "\n" + msg
    }
}
