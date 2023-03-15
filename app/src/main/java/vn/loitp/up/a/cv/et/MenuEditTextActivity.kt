package vn.loitp.up.a.cv.et

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.up.a.cv.et.animatedExpandable.AnimatedExpandableEditTextActivity
import vn.loitp.up.a.cv.et.autoResize.AutoResizeEditTextActivity
import vn.loitp.up.a.cv.et.autoSuggest.EditTextAutoSuggestActivity
import vn.loitp.up.a.cv.et.currency.CurrencyEditTextActivity
import vn.loitp.up.a.cv.et.l.LEditTextActivity
import vn.loitp.up.a.cv.et.materialTextField.MaterialTextFieldActivity
import vn.loitp.up.a.cv.et.otpView.OtpViewActivity
import vn.loitp.up.a.cv.et.textWatcher.EditTextTextWatcherActivity
import vn.loitp.databinding.AEtMenuBinding

@LogTag("EditTextMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuEditTextActivity : BaseActivityFont() {
    private lateinit var binding: AEtMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AEtMenuBinding.inflate(layoutInflater)
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuEditTextActivity::class.java.simpleName
        }
        binding.btAnimatedExpandableEditText.setSafeOnClickListener {
            launchActivity(AnimatedExpandableEditTextActivity::class.java)
        }
        binding.btAutoResizeEditText.setSafeOnClickListener {
            launchActivity(AutoResizeEditTextActivity::class.java)
        }
        binding.btMaterialTextField.setSafeOnClickListener {
            launchActivity(MaterialTextFieldActivity::class.java)
        }
        binding.btAutoSuggestEditText.setSafeOnClickListener {
            launchActivity(EditTextAutoSuggestActivity::class.java)
        }
        binding.btLEditText.setSafeOnClickListener {
            launchActivity(LEditTextActivity::class.java)
        }
        binding.btEditextTextWatcher.setSafeOnClickListener {
            launchActivity(EditTextTextWatcherActivity::class.java)
        }
        binding.btCurrencyEditText.setSafeOnClickListener {
            launchActivity(CurrencyEditTextActivity::class.java)
        }
        binding.btOTPView.setSafeOnClickListener {
            launchActivity(OtpViewActivity::class.java)
        }
    }
}
