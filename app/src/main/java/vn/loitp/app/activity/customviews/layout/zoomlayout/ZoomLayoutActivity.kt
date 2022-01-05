package vn.loitp.app.activity.customviews.layout.zoomlayout

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_layout_zoom.*
import vn.loitp.app.R

// read more
// https://github.com/natario1/ZoomLayout

@LogTag("ZoomLayoutActivity")
@IsFullScreen(false)
class ZoomLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_zoom
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt1.setOnClickListener {
            showShortInformation("Click button bt_1")
        }
        bt2.setOnClickListener {
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
