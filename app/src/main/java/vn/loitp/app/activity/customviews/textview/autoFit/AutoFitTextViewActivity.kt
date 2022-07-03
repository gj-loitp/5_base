package vn.loitp.app.activity.customviews.textview.autoFit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_text_view_auto_fit.*
import vn.loitp.app.R

@LogTag("AutoFitTextViewActivity")
@IsFullScreen(false)
class AutoFitTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_auto_fit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
