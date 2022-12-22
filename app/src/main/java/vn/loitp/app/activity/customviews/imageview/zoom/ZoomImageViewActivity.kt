package vn.loitp.app.activity.customviews.imageview.zoom

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.iv.zoom.ColorGridDrawable
import kotlinx.android.synthetic.main.activity_zoom_image_view.*
import vn.loitp.app.R

@LogTag("ZoomImageViewActivity")
@IsFullScreen(false)
class ZoomImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_zoom_image_view
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
            this.ivIconRight?.isVisible = false
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
