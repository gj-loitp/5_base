package vn.loitp.app.activity.customviews.layout.circularview

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.views.layout.circularview.CircularView
import com.views.layout.circularview.Marker
import kotlinx.android.synthetic.main.activity_layout_circular_view.*
import vn.loitp.app.R

@LogTag("CircularViewActivity")
@IsFullScreen(false)
class CircularViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_circular_view
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/sababado/CircularView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = CircularViewActivity::class.java.simpleName
        }
        val simpleCircularViewAdapter = SimpleCircularViewAdapter()
        circularView.adapter = simpleCircularViewAdapter
        circularView.setOnCircularViewObjectClickListener(object : CircularView.OnClickListener {
            override fun onClick(view: CircularView, isLongClick: Boolean) {
                showShortInformation("onClick")
            }

            override fun onMarkerClick(
                view: CircularView,
                marker: Marker,
                position: Int,
                isLongClick: Boolean
            ) {
                showShortInformation("onClick $position")
            }
        })
    }
}
