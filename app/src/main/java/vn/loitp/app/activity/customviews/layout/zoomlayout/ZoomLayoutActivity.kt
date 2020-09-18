package vn.loitp.app.activity.customviews.layout.zoomlayout

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_layout_zoom.*
import vn.loitp.app.R

//read more
//https://github.com/natario1/ZoomLayout/?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6282

@LayoutId(R.layout.activity_layout_zoom)
@LogTag("ZoomLayoutActivity")
class ZoomLayoutActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt1.setOnClickListener {
            showShort("Click button bt_1")
        }
        bt2.setOnClickListener {
            showShort("Click button bt_2")
        }

        /*zoomLayout.getEngine().panTo(x, y, true);
        zoomLayout.getEngine().panBy(deltaX, deltaY, true);
        zoomLayout.getEngine().zoomTo(zoom, true);
        zoomLayout.getEngine().zoomBy(factor, true);
        zoomLayout.getEngine().realZoomTo(realZoom, true);
        zoomLayout.getEngine().moveTo(zoom, x, y, true);*/
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
