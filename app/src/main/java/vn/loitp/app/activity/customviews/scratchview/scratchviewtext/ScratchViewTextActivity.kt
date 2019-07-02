package vn.loitp.app.activity.customviews.scratchview.scratchviewtext

import android.os.Bundle
import android.widget.TextView

import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.views.scratchview.ScratchTextView

class ScratchViewTextActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = findViewById<TextView>(R.id.tv)
        val scratchTextView = findViewById<ScratchTextView>(R.id.scratchview)
        scratchTextView.setRevealListener(object : ScratchTextView.IRevealListener {
            override fun onRevealed(tv: ScratchTextView) {
                textView.text = "onRevealed"
            }

            override fun onRevealPercentChangedListener(stv: ScratchTextView, percent: Float) {
                textView.text = "onRevealPercentChangedListener percent: $percent"
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scratchview_text
    }
}
