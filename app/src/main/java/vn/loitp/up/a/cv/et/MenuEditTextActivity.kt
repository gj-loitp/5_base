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
import vn.loitp.a.cv.et.animatedExpandable.AnimatedExpandableEditTextActivityFont
import vn.loitp.a.cv.et.autoResize.AutoResizeEditTextActivityFont
import vn.loitp.a.cv.et.autoSuggest.EditTextAutoSuggestActivityFont
import vn.loitp.a.cv.et.currency.CurrencyEditTextActivityFont
import vn.loitp.a.cv.et.l.LEditTextActivity
import vn.loitp.a.cv.et.materialTextField.MaterialTextFieldActivityFont
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
            launchActivity(AnimatedExpandableEditTextActivityFont::class.java)
        }
        binding.btAutoResizeEditText.setSafeOnClickListener {
            launchActivity(AutoResizeEditTextActivityFont::class.java)
        }
        binding.btMaterialTextField.setSafeOnClickListener {
            launchActivity(MaterialTextFieldActivityFont::class.java)
        }
        binding.btAutoSuggestEditText.setSafeOnClickListener {
            launchActivity(EditTextAutoSuggestActivityFont::class.java)
        }
        binding.btLEditText.setSafeOnClickListener {
            launchActivity(LEditTextActivity::class.java)
        }
        binding.btEditextTextWatcher.setSafeOnClickListener {
            launchActivity(EditTextTextWatcherActivity::class.java)
        }
        binding.btCurrencyEditText.setSafeOnClickListener {
            launchActivity(CurrencyEditTextActivityFont::class.java)
        }
        binding.btOTPView.setSafeOnClickListener {
            launchActivity(OtpViewActivity::class.java)
        }
    }
}
