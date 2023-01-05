package vn.loitp.a.cv.et.currency

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_et_currency.*
import vn.loitp.R

@LogTag("CurrencyEditTextActivity")
@IsFullScreen(false)
class CurrencyEditTextActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_et_currency
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CurrencyEditTextActivityFont::class.java.simpleName
        }
        currencyEditText.onTextChanged = { numDot, numNoDot ->
            tvCurrency.text = "$numDot -> $numNoDot"
        }
    }
}
