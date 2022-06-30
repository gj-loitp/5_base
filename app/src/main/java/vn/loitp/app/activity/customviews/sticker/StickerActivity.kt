package vn.loitp.app.activity.customviews.sticker

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.sticker.StickerImageView
import com.loitpcore.views.sticker.StickerTextView
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

        setupViews()
    }

    private fun setupViews() {
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
