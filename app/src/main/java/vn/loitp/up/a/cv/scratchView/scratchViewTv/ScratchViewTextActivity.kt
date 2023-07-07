package vn.loitp.up.a.cv.scratchView.scratchViewTv

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.scratch.LScratchTextView
import vn.loitp.R
import vn.loitp.databinding.AScratchTvBinding

@LogTag("ScratchViewTextActivity")
@IsFullScreen(false)
class ScratchViewTextActivity : BaseActivityFont() {
    private lateinit var binding: AScratchTvBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AScratchTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ScratchViewTextActivity::class.java.simpleName
        }

        binding.scratchViewTextView.setRevealListener(object : LScratchTextView.IRevealListener {

            @SuppressLint("SetTextI18n")
            override fun onRevealed(tv: LScratchTextView) {
                binding.textView.text = "onRevealed"
            }

            @SuppressLint("SetTextI18n")
            override fun onRevealPercentChangedListener(stv: LScratchTextView, percent: Float) {
                binding.textView.text = "onRevealPercentChangedListener percent: $percent"
            }
        })
    }
}
