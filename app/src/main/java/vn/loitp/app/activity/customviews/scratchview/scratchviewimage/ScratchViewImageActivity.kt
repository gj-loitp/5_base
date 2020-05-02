package vn.loitp.app.activity.customviews.scratchview.scratchviewimage

import android.os.Bundle
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.views.scratchview.LScratchImageView
import vn.loitp.app.R

class ScratchViewImageActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scratchImageView = findViewById<LScratchImageView>(R.id.sample_image)
        val textView = findViewById<TextView>(R.id.textView)
        scratchImageView.setRevealListener(object : LScratchImageView.IRevealListener {
            override fun onRevealed(tv: LScratchImageView) {
                textView.text = "onRevealed"
            }

            override fun onRevealPercentChangedListener(siv: LScratchImageView, percent: Float) {
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
        return R.layout.activity_scratchview_image
    }
}
