package vn.loitp.up.a.demo.fragmentFlow

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseFragment
import com.loitp.core.base.OnBackPressedListener
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.addFragment
import com.loitp.core.ext.findFragmentByTag
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ADemoFragmentFlowBinding

@LogTag("FragmentFlowActivity")
@IsFullScreen(false)
class FragmentFlowActivity : BaseActivityFont() {

    var onBackClickListener: OnBackPressedListener? = null
    private var doubleBackToExitPressedOnce = false
    private lateinit var binding: ADemoFragmentFlowBinding

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
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener(getListener())

        binding = ADemoFragmentFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = FragmentFlowActivity::class.java.simpleName
        }
        binding.btAddFrmFlow0.setSafeOnClickListener {
            showFragment(FrmFlow0())
        }
        binding.btClearAllFrm.setSafeOnClickListener {
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
        this.addFragment(
            containerFrameLayoutIdRes = R.id.flContainer,
            fragment = baseFragment,
            tag = baseFragment.javaClass.simpleName,
            isAddToBackStack = true
        )
    }

    private fun clearAllFragments() {
        val frmFlow0 =
            this.findFragmentByTag(tag = FrmFlow0::class.java.simpleName)
        frmFlow0?.let {
            (it as BaseFragmentFlow).popThisFragment()
        }
        val frmFlow1 =
            this.findFragmentByTag(tag = FrmFlow1::class.java.simpleName)
        frmFlow1?.let {
            (it as BaseFragmentFlow).popThisFragment()
        }
        val frmFlow2 =
            this.findFragmentByTag(tag = FrmFlow2::class.java.simpleName)
        frmFlow2?.let {
            (it as BaseFragmentFlow).popThisFragment()
        }
    }

    @SuppressLint("SetTextI18n")
    fun print(msg: String) {
        binding.tvInformation.text = binding.tvInformation.text.toString() + "\n" + msg
    }
}
