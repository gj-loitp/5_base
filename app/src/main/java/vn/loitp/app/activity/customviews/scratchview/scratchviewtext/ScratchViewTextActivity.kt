package vn.loitp.app.activity.customviews.scratchview.scratchviewtext

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.scratchview.LScratchTextView
import kotlinx.android.synthetic.main.activity_scratchview_text.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_scratchview_text)
@LogTag("ScratchViewTextActivity")
class ScratchViewTextActivity : BaseFontActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scratchViewTextView.setRevealListener(object : LScratchTextView.IRevealListener {

            override fun onRevealed(tv: LScratchTextView) {
                textView.text = "onRevealed"
            }

            override fun onRevealPercentChangedListener(stv: LScratchTextView, percent: Float) {
                textView.text = "onRevealPercentChangedListener percent: $percent"
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
