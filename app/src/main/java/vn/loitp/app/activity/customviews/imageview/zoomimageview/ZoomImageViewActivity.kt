package vn.loitp.app.activity.customviews.imageview.zoomimageview

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.imageview.zoom.ColorGridDrawable
import kotlinx.android.synthetic.main.activity_zoomimageview.*
import loitp.basemaster.R

class ZoomImageViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_zoomimageview
    }

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
