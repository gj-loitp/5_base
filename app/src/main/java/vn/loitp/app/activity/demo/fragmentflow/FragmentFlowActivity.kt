package vn.loitp.app.activity.demo.fragmentflow

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.FragmentManager
import com.core.base.BaseFontActivity
import com.core.base.BaseFragment
import com.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_fragment_flow.*
import loitp.basemaster.R

class FragmentFlowActivity : BaseFontActivity() {

    var onBackClickListener: OnBackClickListener? = null
    private var doubleBackToExitPressedOnce = false

    private fun getListener(): FragmentManager.OnBackStackChangedListener {
        return FragmentManager.OnBackStackChangedListener {
            //print("OnBackStackChangedListener")
            supportFragmentManager?.let {
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

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fragment_flow
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
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    fun showFragment(baseFragment: BaseFragment) {
        LScreenUtil.addFragment(activity, R.id.flContainer, baseFragment, baseFragment.javaClass.simpleName, true)
    }

    private fun clearAllFragments() {
        val frmFlow0 = LScreenUtil.findFragmentByTag(activity, FrmFlow0::class.java.simpleName)
        frmFlow0?.let {
            (it as FrmFlowBase).popThisFragment()
        }
        val frmFlow1 = LScreenUtil.findFragmentByTag(activity, FrmFlow1::class.java.simpleName)
        frmFlow1?.let {
            (it as FrmFlowBase).popThisFragment()
        }
        val frmFlow2 = LScreenUtil.findFragmentByTag(activity, FrmFlow2::class.java.simpleName)
        frmFlow2?.let {
            (it as FrmFlowBase).popThisFragment()
        }
    }

    fun print(msg: String) {
        tvInformation.text = tvInformation.text.toString() + "\n" + msg
    }
}
