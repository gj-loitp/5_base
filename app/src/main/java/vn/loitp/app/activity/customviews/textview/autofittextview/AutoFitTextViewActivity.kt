package vn.loitp.app.activity.customviews.textview.autofittextview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_text_view_autofit.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_text_view_autofit)
@LogTag("AutoFitTextViewActivity")
@IsFullScreen(false)
class AutoFitTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
                // do nothing
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
                tvOutput.text = charSequence
                textViewAutoFit.text = charSequence
            }

            override fun afterTextChanged(editable: Editable) {
                // do nothing
            }
        })
    }

}
