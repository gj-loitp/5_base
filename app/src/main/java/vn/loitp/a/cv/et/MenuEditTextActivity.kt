package vn.loitp.a.cv.et

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_et_menu.*
import vn.loitp.R
import vn.loitp.a.cv.et.animatedExpandable.AnimatedExpandableEditTextActivity
import vn.loitp.a.cv.et.autoResize.AutoResizeEditTextActivity
import vn.loitp.a.cv.et.autoSuggest.EditTextAutoSuggestActivity
import vn.loitp.a.cv.et.currency.CurrencyEditTextActivity
import vn.loitp.a.cv.et.l.LEditTextActivity
import vn.loitp.a.cv.et.materialTextField.MaterialTextFieldActivity
import vn.loitp.a.cv.et.otpView.OtpViewActivity
import vn.loitp.a.cv.et.textWatcher.EditTextTextWatcherActivity

@LogTag("EditTextMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuEditTextActivity : BaseFontActivity() {

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
            this.tvTitle?.text = MenuEditTextActivity::class.java.simpleName
        }
        btAnimatedExpandableEditText.setSafeOnClickListener {
            launchActivity(AnimatedExpandableEditTextActivity::class.java)
        }
        btAutoResizeEditText.setSafeOnClickListener {
            launchActivity(AutoResizeEditTextActivity::class.java)
        }
        btMaterialTextField.setSafeOnClickListener {
            launchActivity(MaterialTextFieldActivity::class.java)
        }
        btAutoSuggestEditText.setSafeOnClickListener {
            launchActivity(EditTextAutoSuggestActivity::class.java)
        }
        btLEditText.setSafeOnClickListener {
            launchActivity(LEditTextActivity::class.java)
        }
        btEditextTextWatcher.setSafeOnClickListener {
            launchActivity(EditTextTextWatcherActivity::class.java)
        }
        btCurrencyEditText.setSafeOnClickListener {
            launchActivity(CurrencyEditTextActivity::class.java)
        }
        btOTPView.setSafeOnClickListener {
            launchActivity(OtpViewActivity::class.java)
        }
    }
}
