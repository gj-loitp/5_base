package vn.loitp.up.a.cv.iv.fidgetSpinner

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AIvFidgetSpinnerBinding

@LogTag("FidgetSpinnerIvActivity")
@IsFullScreen(false)
class FidgetSpinnerIvActivity : BaseActivityFont() {
    private lateinit var binding: AIvFidgetSpinnerBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIvFidgetSpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = FidgetSpinnerIvActivity::class.java.simpleName
        }
        binding.fidgetSpinner.setImageDrawable(R.drawable.spinner)
    }
}
