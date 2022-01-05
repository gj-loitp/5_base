package vn.loitp.app.activity.customviews.imageview.panoramaimageview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.imageview.panorama.GyroscopeObserver
import com.views.imageview.panorama.LPanoramaImageView
import kotlinx.android.synthetic.main.activity_imageview_panorama.*
import vn.loitp.app.R

// https://github.com/gjiazhe/PanoramaImageView

@LogTag("PanoramaImageViewActivity")
@IsFullScreen(false)
class PanoramaImageViewActivity : BaseFontActivity() {
    private var gyroscopeObserver: GyroscopeObserver? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_panorama
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        gyroscopeObserver = GyroscopeObserver()
        // Set the maximum radian the device should rotate to show image's bounds.
        // It should be set between 0 and π/2.
        // The default value is π/9.

        gyroscopeObserver?.setMaxRotateRadian(Math.PI / 9)

        // panoramaImageView.setEnablePanoramaMode(true);
        // panoramaImageView.setEnableScrollbar(true);
        // panoramaImageView.setInvertScrollDirection(false);
        // Set GyroscopeObserver for PanoramaImageView.
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver)

        panoramaImageView.setOnPanoramaScrollListener(object :
                LPanoramaImageView.OnPanoramaScrollListener {
                override fun onScrolled(view: LPanoramaImageView?, offsetProgress: Float) {
                    logD("onScrolled offsetProgress $offsetProgress")
                }
            })
    }

    override fun onResume() {
        super.onResume()
        // Register GyroscopeObserver.
        gyroscopeObserver?.register(this)
    }

    override fun onPause() {
        super.onPause()
        // Unregister GyroscopeObserver.
        gyroscopeObserver?.unregister()
    }
}
