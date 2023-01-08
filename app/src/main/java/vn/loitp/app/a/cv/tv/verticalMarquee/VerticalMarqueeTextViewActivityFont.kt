package vn.loitp.app.a.cv.tv.verticalMarquee

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.activity_text_view_vertical_marque.*
import vn.loitp.R

@LogTag("VerticalMarqueeTextViewActivity")
@IsFullScreen(false)
class VerticalMarqueeTextViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_vertical_marque
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = VerticalMarqueeTextViewActivityFont::class.java.simpleName
        }
    }
}
