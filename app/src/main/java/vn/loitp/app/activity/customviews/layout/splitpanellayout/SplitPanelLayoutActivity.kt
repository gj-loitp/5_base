package vn.loitp.app.activity.customviews.layout.splitpanellayout

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.layout.splitpanellayout.SplitPaneLayout
import kotlinx.android.synthetic.main.activity_layout_split_panel.*
import vn.loitp.app.R
import java.text.DecimalFormat
import java.util.*

@LogTag("SplitPanelLayoutActivity")
@IsFullScreen(false)
class SplitPanelLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_split_panel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splitPaneLayout.onSplitterPositionChangedListener = object : SplitPaneLayout.OnSplitterPositionChangedListener {
            override fun onSplitterPositionChanged(splitPaneLayout: SplitPaneLayout, fromUser: Boolean) {
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
