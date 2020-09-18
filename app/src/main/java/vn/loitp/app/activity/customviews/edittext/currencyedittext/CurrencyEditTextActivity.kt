package vn.loitp.app.activity.customviews.edittext.currencyedittext

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_edittext_currency.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_edittext_currency)
@LogTag("CurrencyEditTextActivity")
class CurrencyEditTextActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currencyEditText.onTextChanged = { numDot, numNoDot ->
            tvCurrency.text = "$numDot -> $numNoDot"
        }
    }
}
