package vn.loitp.a.cv.et

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_et_menu.*
import vn.loitp.R
import vn.loitp.a.cv.et.animatedExpandable.AnimatedExpandableEditTextActivityFont
import vn.loitp.a.cv.et.autoResize.AutoResizeEditTextActivityFont
import vn.loitp.a.cv.et.autoSuggest.EditTextAutoSuggestActivityFont
import vn.loitp.a.cv.et.currency.CurrencyEditTextActivityFont
import vn.loitp.a.cv.et.l.LEditTextActivityFont
import vn.loitp.a.cv.et.materialTextField.MaterialTextFieldActivityFont
import vn.loitp.a.cv.et.otpView.OtpViewActivityFont
import vn.loitp.a.cv.et.textWatcher.EditTextTextWatcherActivityFont

@LogTag("EditTextMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuEditTextActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_et_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuEditTextActivityFont::class.java.simpleName
        }
        btAnimatedExpandableEditText.setSafeOnClickListener {
            launchActivity(AnimatedExpandableEditTextActivityFont::class.java)
        }
        btAutoResizeEditText.setSafeOnClickListener {
            launchActivity(AutoResizeEditTextActivityFont::class.java)
        }
        btMaterialTextField.setSafeOnClickListener {
            launchActivity(MaterialTextFieldActivityFont::class.java)
        }
        btAutoSuggestEditText.setSafeOnClickListener {
            launchActivity(EditTextAutoSuggestActivityFont::class.java)
        }
        btLEditText.setSafeOnClickListener {
            launchActivity(LEditTextActivityFont::class.java)
        }
        btEditextTextWatcher.setSafeOnClickListener {
            launchActivity(EditTextTextWatcherActivityFont::class.java)
        }
        btCurrencyEditText.setSafeOnClickListener {
            launchActivity(CurrencyEditTextActivityFont::class.java)
        }
        btOTPView.setSafeOnClickListener {
            launchActivity(OtpViewActivityFont::class.java)
        }
    }
}
