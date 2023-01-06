package vn.loitp.a.cv.layout.scrollView2d

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setDelay
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_layout_scroll_view_2d.*
import vn.loitp.R

@LogTag("ScrollView2DActivity")
@IsFullScreen(false)
class ScrollView2DActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_scroll_view_2d
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ScrollView2DActivityFont::class.java.simpleName
        }
        twoDScrollView.setScrollChangeListener { _, x, y, oldX, oldY ->
            logD("setScrollChangeListener $x - $y - $oldX - $oldY")
        }
        setDelay(
            mls = 2000,
            runnable = {
                twoDScrollView.smoothScrollTo(300, 300)
            }
        )
    }
}
