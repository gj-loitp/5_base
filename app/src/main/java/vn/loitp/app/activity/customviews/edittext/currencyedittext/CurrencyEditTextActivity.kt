package vn.loitp.app.activity.customviews.edittext.currencyedittext

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_edittext_currency.*
import vn.loitp.app.R

@LogTag("CurrencyEditTextActivity")
@IsFullScreen(false)
class CurrencyEditTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_edittext_currency
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = CurrencyEditTextActivity::class.java.simpleName
        }
        currencyEditText.onTextChanged = { numDot, numNoDot ->
            tvCurrency.text = "$numDot -> $numNoDot"
        }
    }
}
