package vn.loitp.up.a.cv.et.pinEditTextField

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.poovam.pinedittextfield.PinField
import vn.loitp.R
import vn.loitp.databinding.APinEditTextFieldBinding


@LogTag("PinEditTextFieldActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class PinEditTextFieldActivity : BaseActivityFont() {

    private lateinit var binding: APinEditTextFieldBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APinEditTextFieldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/poovamraj/PinEditTextField"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = PinEditTextFieldActivity::class.java.simpleName
        }

        binding.lineField.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {
                showShortInformation(enteredText)
                return true; // Return false to keep the keyboard open else return true to close the keyboard
            }

        }
    }
}
