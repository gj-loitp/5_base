package vn.loitp.a.cv.iv.panorama

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.iv.panorama.GyroscopeObserver
import com.loitp.views.iv.panorama.LPanoramaImageView
import kotlinx.android.synthetic.main.a_iv_panorama.*
import vn.loitp.R
import vn.loitp.app.EmptyActivity

@LogTag("PanoramaIvActivity")
@IsFullScreen(false)
class PanoramaIvActivityFont : BaseActivityFont() {
    private var gyroscopeObserver: GyroscopeObserver? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_panorama
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
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
