package vn.loitp.app.a.cv.scratchView.scratchViewImage

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.scratch.LScratchImageView
import kotlinx.android.synthetic.main.activity_scratch_view_image.*
import vn.loitp.R

@LogTag("ScratchViewImageActivity")
@IsFullScreen(false)
class ScratchViewImageActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scratch_view_image
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ScratchViewImageActivity::class.java.simpleName
        }
        scratchImageView.setRevealListener(object : LScratchImageView.IRevealListener {
            @SuppressLint("SetTextI18n")
            override fun onRevealed(iv: LScratchImageView) {
                textView.text = "onRevealed"
            }

            @SuppressLint("SetTextI18n")
            override fun onRevealPercentChangedListener(siv: LScratchImageView, percent: Float) {
                textView.text = "onRevealPercentChangedListener percent: $percent"
            }
        })
    }
}
