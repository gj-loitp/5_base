package vn.loitp.up.a.cv.et.textWatcher

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.addTextChangedDelayListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AEtTextWatcherBinding

@LogTag("EditTextTextWatcherActivity")
@IsFullScreen(false)
class EditTextTextWatcherActivity : BaseActivityFont() {
    private lateinit var binding: AEtTextWatcherBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AEtTextWatcherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = EditTextTextWatcherActivity::class.java.simpleName
        }
        var text = ""
        binding.editText.addTextChangedDelayListener(delayInMls = 1000, afterTextChanged = { s ->
            text += s + "\n"
            binding.textView.text = text
        })
    }
}
