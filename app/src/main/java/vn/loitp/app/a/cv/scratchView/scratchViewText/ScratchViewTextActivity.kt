package vn.loitp.app.a.cv.scratchView.scratchViewText

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.scratch.LScratchTextView
import kotlinx.android.synthetic.main.activity_scratch_view_text.*
import vn.loitp.R

@LogTag("ScratchViewTextActivity")
@IsFullScreen(false)
class ScratchViewTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scratch_view_text
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
