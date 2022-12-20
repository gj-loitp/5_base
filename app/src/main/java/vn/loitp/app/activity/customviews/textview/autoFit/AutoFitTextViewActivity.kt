package vn.loitp.app.activity.customviews.textview.autoFit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
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
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = AutoFitTextViewActivity::class.java.simpleName
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
