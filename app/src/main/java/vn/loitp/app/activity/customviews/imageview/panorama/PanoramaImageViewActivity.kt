package vn.loitp.app.activity.customviews.imageview.panorama

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.loitpcore.views.imageView.panorama.GyroscopeObserver
import com.loitpcore.views.imageView.panorama.LPanoramaImageView
import kotlinx.android.synthetic.main.activity_panorama_image_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.EmptyActivity

@LogTag("PanoramaImageViewActivity")
@IsFullScreen(false)
class PanoramaImageViewActivity : BaseFontActivity() {
    private var gyroscopeObserver: GyroscopeObserver? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_panorama_image_view
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
                            url = "https://github.com/gjiazhe/PanoramaImageView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }
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
