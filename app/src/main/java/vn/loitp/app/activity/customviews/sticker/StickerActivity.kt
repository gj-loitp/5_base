package vn.loitp.app.activity.customviews.sticker

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.views.sticker.StickerImageView
import com.views.sticker.StickerTextView
import kotlinx.android.synthetic.main.activity_sticker.*

import vn.loitp.app.R

class StickerActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // add a stickerImage to canvas
        val ivSticker = StickerImageView(activity)
        //iv_sticker.setImageDrawable(((ImageView) view.findViewById(R.id.iv_sticker)).getDrawable());
        ivSticker.setBackgroundResource(R.drawable.logo)
        flCanvas.addView(ivSticker)

        // add a stickerText to canvas
        val tvSticker = StickerTextView(activity)
        tvSticker.text = "Demo StickerText"
        flCanvas.addView(tvSticker)

    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sticker
    }
}
