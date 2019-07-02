package vn.loitp.app.activity.customviews.scratchview.scratchviewimage

import android.os.Bundle
import android.widget.TextView

import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.views.scratchview.ScratchImageView

class ScratchViewImageActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scratchImageView = findViewById<ScratchImageView>(R.id.sample_image)
        val textView = findViewById<TextView>(R.id.tv)
        scratchImageView.setRevealListener(object : ScratchImageView.IRevealListener {
            override fun onRevealed(tv: ScratchImageView) {
                textView.text = "onRevealed"
            }

            override fun onRevealPercentChangedListener(siv: ScratchImageView, percent: Float) {
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
