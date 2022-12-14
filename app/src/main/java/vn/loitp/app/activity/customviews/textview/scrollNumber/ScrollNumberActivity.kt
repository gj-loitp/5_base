package vn.loitp.app.activity.customviews.textview.scrollNumber

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_text_view_scroll_number.*
import vn.loitp.app.R

@LogTag("ScrollNumberActivity")
@IsFullScreen(false)
class ScrollNumberActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_scroll_number
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ScrollNumberActivity::class.java.simpleName
        }
        multiScrollNumber.setTextColors(
            intArrayOf(
                R.color.red50,
                R.color.orange,
                R.color.blue,
                R.color.green,
                R.color.purple
            )
        )
        // scrollNumber.setTextSize(64)

        // scrollNumber.setNumber(64, 2048)
        // scrollNumber.setInterpolator(new DecelerateInterpolator())

        multiScrollNumber.setScrollVelocity(20)
        multiScrollNumber.setNumber(20.48)
    }
}
