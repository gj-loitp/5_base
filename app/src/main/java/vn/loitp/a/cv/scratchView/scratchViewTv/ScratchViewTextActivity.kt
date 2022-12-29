package vn.loitp.a.cv.scratchView.scratchViewTv

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.scratch.LScratchTextView
import kotlinx.android.synthetic.main.a_scratch_tv.*
import vn.loitp.R

@LogTag("ScratchViewTextActivity")
@IsFullScreen(false)
class ScratchViewTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_scratch_tv
    }

    @SuppressLint("SetTextI18n")
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
            this.tvTitle?.text = ScratchViewTextActivity::class.java.simpleName
        }

        scratchViewTextView.setRevealListener(object : LScratchTextView.IRevealListener {

            @SuppressLint("SetTextI18n")
            override fun onRevealed(tv: LScratchTextView) {
                textView.text = "onRevealed"
            }

            @SuppressLint("SetTextI18n")
            override fun onRevealPercentChangedListener(stv: LScratchTextView, percent: Float) {
                textView.text = "onRevealPercentChangedListener percent: $percent"
            }
        })
    }
}