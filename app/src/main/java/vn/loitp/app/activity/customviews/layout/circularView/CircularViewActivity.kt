package vn.loitp.app.activity.customviews.layout.circularView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.layout.circularView.CircularView
import com.loitpcore.views.layout.circularView.Marker
import kotlinx.android.synthetic.main.activity_circular_view_layout.*
import vn.loitp.app.R

@LogTag("CircularViewActivity")
@IsFullScreen(false)
class CircularViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_circular_view_layout
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
