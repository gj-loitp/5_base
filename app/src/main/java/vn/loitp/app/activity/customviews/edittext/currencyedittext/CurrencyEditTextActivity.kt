package vn.loitp.app.activity.customviews.edittext.currencyedittext

import com.core.base.BaseFontActivity
import vn.loitp.app.R

class CurrencyEditTextActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_edittext_currency
    }

}
