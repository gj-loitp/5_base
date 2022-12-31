package vn.loitp.a.cv.layout.zoom

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_layout_zoom.*
import vn.loitp.R

@LogTag("ZoomLayoutActivity")
@IsFullScreen(false)
class ZoomLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_zoom
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
                            url = "https://github.com/natario1/ZoomLayout"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ZoomLayoutActivity::class.java.simpleName
        }
        bt1.setSafeOnClickListener {
            showShortInformation("Click button bt_1")
        }
        bt2.setSafeOnClickListener {
            showShortInformation("Click button bt_2")
        }

        /*zoomLayout.getEngine().panTo(x, y, true);
        zoomLayout.getEngine().panBy(deltaX, deltaY, true);
        zoomLayout.getEngine().zoomTo(zoom, true);
        zoomLayout.getEngine().zoomBy(factor, true);
        zoomLayout.getEngine().realZoomTo(realZoom, true);
        zoomLayout.getEngine().moveTo(zoom, x, y, true);*/
    }
}
