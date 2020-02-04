package vn.loitp.app.activity.customviews.scratchview.scratchviewtext

import android.os.Bundle
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.views.scratchview.LScratchTextView
import vn.loitp.app.R

class ScratchViewTextActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = findViewById<TextView>(R.id.tv)
        val scratchTextView = findViewById<LScratchTextView>(R.id.scratchview)
        scratchTextView.setRevealListener(object : LScratchTextView.IRevealListener {
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

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scratchview_text
    }
}
