package vn.loitp.app.activity.customviews.sticker

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag

import com.core.base.BaseFontActivity
import com.views.sticker.StickerImageView
import com.views.sticker.StickerTextView
import kotlinx.android.synthetic.main.activity_sticker.*

import vn.loitp.app.R

@LogTag("StickerActivity")
@IsFullScreen(false)
class StickerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sticker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // add a stickerImage to canvas
        val ivSticker = StickerImageView(this)
        ivSticker.setBackgroundResource(R.drawable.logo)
        flCanvas.addView(ivSticker)

        // add a stickerText to canvas
        val tvSticker = StickerTextView(this)
        tvSticker.text = "Demo StickerText"
        flCanvas.addView(tvSticker)

    }

}
