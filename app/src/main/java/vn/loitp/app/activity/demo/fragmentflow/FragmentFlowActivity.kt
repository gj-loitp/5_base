package vn.loitp.app.activity.demo.fragmentflow

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.FragmentManager
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.base.BaseFragment
import com.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_demo_fragment_flow.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_demo_fragment_flow)
@LogTag("FragmentFlowActivity")
@IsFullScreen(false)
class FragmentFlowActivity : BaseFontActivity() {

    var onBackClickListener: OnBackClickListener? = null
    private var doubleBackToExitPressedOnce = false

    private fun getListener(): FragmentManager.OnBackStackChangedListener {
        return FragmentManager.OnBackStackChangedListener {
            //print("OnBackStackChangedListener")
            supportFragmentManager.let {
                val currFrag = it.findFragmentById(R.id.flContainer) as FrmFlowBase?
                currFrag?.onFragmentResume()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener(getListener())

        btAddFrmFlow0.setOnClickListener {
            showFragment(FrmFlow0())
        }
        btClearAllFrm.setOnClickListener {
            clearAllFragments()
        }
    }

    override fun onBackPressed() {
        if (onBackClickListener != null && onBackClickListener!!.onBackClick()) {
            return
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        showShort("Press again to exit")
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    fun showFragment(baseFragment: BaseFragment) {
        LScreenUtil.addFragment(this, R.id.flContainer, baseFragment, baseFragment.javaClass.simpleName, true)
    }

    private fun clearAllFragments() {
        val frmFlow0 = LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow0::class.java.simpleName)
        frmFlow0?.let {
            (it as FrmFlowBase).popThisFragment()
        }
        val frmFlow1 = LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow1::class.java.simpleName)
        frmFlow1?.let {
            (it as FrmFlowBase).popThisFragment()
        }
        val frmFlow2 = LScreenUtil.findFragmentByTag(activity = this, tag = FrmFlow2::class.java.simpleName)
        frmFlow2?.let {
            (it as FrmFlowBase).popThisFragment()
        }
    }

    @SuppressLint("SetTextI18n")
    fun print(msg: String) {
        tvInformation.text = tvInformation.text.toString() + "\n" + msg
    }
}
