package vn.loitp.app.a.cv.et.currency

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_currency_edittext.*
import vn.loitp.R

@LogTag("CurrencyEditTextActivity")
@IsFullScreen(false)
class CurrencyEditTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_currency_edittext
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
            this.tvTitle?.text = CurrencyEditTextActivity::class.java.simpleName
        }
        currencyEditText.onTextChanged = { numDot, numNoDot ->
            tvCurrency.text = "$numDot -> $numNoDot"
        }
    }
}