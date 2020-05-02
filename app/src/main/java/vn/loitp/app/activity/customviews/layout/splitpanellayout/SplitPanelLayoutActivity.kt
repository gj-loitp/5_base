package vn.loitp.app.activity.customviews.layout.splitpanellayout

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.layout.splitpanellayout.SplitPaneLayout
import kotlinx.android.synthetic.main.activity_layout_split_panel.*
import vn.loitp.app.R
import java.text.DecimalFormat
import java.util.*

class SplitPanelLayoutActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splitPaneLayout.onSplitterPositionChangedListener = SplitPaneLayout.OnSplitterPositionChangedListener { _, _ ->
            val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
            tvFirst.text = percent.format(splitPaneLayout.splitterPositionPercent)
            tvSecond.text = percent.format(1f - splitPaneLayout.splitterPositionPercent)
        }
        splitPaneLayout.post(Runnable {
            val percent = DecimalFormat.getPercentInstance(Locale.getDefault())
            tvFirst.text = percent.format(splitPaneLayout.splitterPositionPercent)
            tvSecond.text = percent.format(1f - splitPaneLayout.splitterPositionPercent)
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_split_panel
    }
}
