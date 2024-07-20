package vn.loitp.up.a.cv.sticker

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.sticker.StickerImageView
import com.loitp.views.sticker.StickerTextView
import vn.loitp.R
import vn.loitp.databinding.AStickerBinding

@LogTag("StickerActivity")
@IsFullScreen(false)
class StickerActivity : BaseActivityFont() {
    private lateinit var binding: AStickerBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AStickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = StickerActivity::class.java.simpleName
        }
        // add a stickerImage to canvas
        val ivSticker = StickerImageView(this)
        ivSticker.setBackgroundResource(R.drawable.logo)
        binding.flCanvas.addView(ivSticker)

        // add a stickerText to canvas
        val tvSticker = StickerTextView(this)
        tvSticker.text = "Demo StickerText"
        binding.flCanvas.addView(tvSticker)
    }
}
