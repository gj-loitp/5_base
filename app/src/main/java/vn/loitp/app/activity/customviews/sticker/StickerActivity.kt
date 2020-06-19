package vn.loitp.app.activity.customviews.sticker

import android.os.Bundle
import android.widget.FrameLayout

import com.core.base.BaseFontActivity
import com.views.sticker.StickerImageView
import com.views.sticker.StickerTextView

import vn.loitp.app.R

class StickerActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val canvas = findViewById<FrameLayout>(R.id.vg_canvas)
        // add a stickerImage to canvas
        val ivSticker = StickerImageView(activity)
        //iv_sticker.setImageDrawable(((ImageView) view.findViewById(R.id.iv_sticker)).getDrawable());
        ivSticker.setBackgroundResource(R.drawable.logo)
        canvas.addView(ivSticker)

        // add a stickerText to canvas
        val tv_sticker = StickerTextView(activity)
        tv_sticker.text = "Demo StickerText"
        canvas.addView(tv_sticker)

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
