package vn.loitp.a.cv.layout.splitPanel

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.layout.splitPanel.SplitPaneLayout
import kotlinx.android.synthetic.main.a_layout_split_panel.*
import vn.loitp.R
import java.text.DecimalFormat
import java.util.*

@LogTag("SplitPanelLayoutActivity")
@IsFullScreen(false)
class SplitPanelLayoutActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_split_panel
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = SplitPanelLayoutActivityFont::class.java.simpleName
        }
        splitPaneLayout.onSplitterPositionChangedListener =
            object : SplitPaneLayout.OnSplitterPositionChangedListener {
                override fun onSplitterPositionChanged(
                    splitPaneLayout: SplitPaneLayout,
                    fromUser: Boolean
                ) {
                    val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
                    tvFirst.text = percent.format(splitPaneLayout.splitterPositionPercent)
                    tvSecond.text = percent.format(1f - splitPaneLayout.splitterPositionPercent)
                }
            }

        splitPaneLayout.post {
            val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
            tvFirst.text = percent.format(splitPaneLayout.splitterPositionPercent)
            tvSecond.text = percent.format(1f - splitPaneLayout.splitterPositionPercent)
        }
    }
}
