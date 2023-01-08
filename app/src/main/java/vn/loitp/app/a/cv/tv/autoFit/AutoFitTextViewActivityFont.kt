package vn.loitp.app.a.cv.tv.autoFit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.activity_text_view_auto_fit.*
import vn.loitp.R

@LogTag("AutoFitTextViewActivity")
@IsFullScreen(false)
class AutoFitTextViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_auto_fit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = AutoFitTextViewActivityFont::class.java.simpleName
        }
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
