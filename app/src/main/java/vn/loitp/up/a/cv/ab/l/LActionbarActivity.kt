package vn.loitp.up.a.cv.ab.l

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.readTxtFromRawFolder
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ALActionBarBinding

@LogTag("LActionbarActivity")
@IsFullScreen(false)
class LActionbarActivity : BaseActivityFont() {
    private lateinit var binding: ALActionBarBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALActionBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    @SuppressLint("SetTextI18n")
    private fun setupActionBar() {
        binding.textView.text = readTxtFromRawFolder(nameOfRawFile = R.raw.lactionbar)

        binding.lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        showShortInformation(msg = "onClickMenu", isTopAnchor = false)
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = "LActionbarActivity"
        }
    }
}
