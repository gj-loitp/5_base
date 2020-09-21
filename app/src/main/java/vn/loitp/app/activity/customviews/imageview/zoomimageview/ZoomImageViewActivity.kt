package vn.loitp.app.activity.customviews.imageview.zoomimageview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.imageview.zoom.ColorGridDrawable
import kotlinx.android.synthetic.main.activity_imageview_zoom.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_imageview_zoom)
@LogTag("ZoomImageViewActivity")
@IsFullScreen(false)
class ZoomImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zoomImageView.setImageDrawable(ColorGridDrawable())

//        zoomImageView.getEngine().panTo(x, y, true);
//        zoomImageView.getEngine().panBy(deltaX, deltaY, true);
//        zoomImageView.getEngine().zoomTo(zoom, true);
//        zoomImageView.getEngine().zoomBy(factor, true);
//        zoomImageView.getEngine().realZoomTo(realZoom, true);
//        zoomImageView.getEngine().moveTo(zoom, x, y, true);
    }
}
