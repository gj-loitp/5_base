package vn.loitp.app.activity.customviews.imageview.zoomimageview

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.views.imageview.zoom.ColorGridDrawable
import kotlinx.android.synthetic.main.activity_imageview_zoom.*
import vn.loitp.app.R

@LogTag("ZoomImageViewActivity")
@IsFullScreen(false)
class ZoomImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_zoom
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
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ZoomImageViewActivity::class.java.simpleName
        }
        zoomImageView.setImageDrawable(ColorGridDrawable())

//        zoomImageView.getEngine().panTo(x, y, true);
//        zoomImageView.getEngine().panBy(deltaX, deltaY, true);
//        zoomImageView.getEngine().zoomTo(zoom, true);
//        zoomImageView.getEngine().zoomBy(factor, true);
//        zoomImageView.getEngine().realZoomTo(realZoom, true);
//        zoomImageView.getEngine().moveTo(zoom, x, y, true);
    }
}
