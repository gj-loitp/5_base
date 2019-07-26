package vn.loitp.app.activity.customviews.edittext.autosuggest

import android.graphics.Color
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.views.edittext.autosuggesttextview.LAutoSuggestEditText
import kotlinx.android.synthetic.main.activity_editext_auto_suggest.*
import loitp.basemaster.R

class EditTextAutoSuggestActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aet0.setColorProgressBar(Color.RED)
        aet0.setBackgroundResource(R.drawable.bkg_et)
        aet0.callback = object : LAutoSuggestEditText.Callback {
            override fun onTextChanged(text: String) {
                LLog.d(TAG, "onTextChanged $text")
            }
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_auto_suggest
    }


}
