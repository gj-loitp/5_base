package vn.loitp.a.cv.iv.continuousScrollable

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_iv_continuous_scrollable.*
import vn.loitp.R
import vn.loitp.app.EmptyActivityFont

@LogTag("ContinuousScrollableImageViewActivity")
@IsFullScreen(false)
class ContinuousScrollableImageViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_continuous_scrollable
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
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/Cutta/ContinuousScrollableImageView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivityFont::class.java.simpleName
        }
    }
}
