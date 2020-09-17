package vn.loitp.app.activity.customviews.scratchview.scratchviewimage

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.views.scratchview.LScratchImageView
import kotlinx.android.synthetic.main.activity_scratchview_image.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_scratchview_image)
class ScratchViewImageActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

}
