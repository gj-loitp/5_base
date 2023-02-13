package vn.loitp.up.a.cv.scratchView.scratchViewIv

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.scratch.LScratchImageView
import vn.loitp.R
import vn.loitp.databinding.AScratchIvBinding

@LogTag("ScratchViewImageActivity")
@IsFullScreen(false)
class ScratchViewImageActivity : BaseActivityFont() {

    private lateinit var binding: AScratchIvBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AScratchIvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ScratchViewImageActivity::class.java.simpleName
        }
        binding.scratchImageView.setRevealListener(object : LScratchImageView.IRevealListener {
            @SuppressLint("SetTextI18n")
            override fun onRevealed(iv: LScratchImageView) {
                binding.textView.text = "onRevealed"
            }

            @SuppressLint("SetTextI18n")
            override fun onRevealPercentChangedListener(siv: LScratchImageView, percent: Float) {
                binding.textView.text = "onRevealPercentChangedListener percent: $percent"
            }
        })
    }
}
