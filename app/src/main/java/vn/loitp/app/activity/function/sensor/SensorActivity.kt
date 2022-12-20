package vn.loitp.app.activity.function.sensor

import android.content.Context
import android.os.Bundle
import android.view.OrientationEventListener
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_func_sensor.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants.Companion.URL_IMG

@LogTag("SensorActivity")
@IsFullScreen(false)
class SensorActivity : BaseFontActivity() {
    private var orientationListener: OrientationListener? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_sensor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        LImageUtil.load(context = this, any = URL_IMG, imageView = imageView)
        val w = LScreenUtil.screenWidth
        val h = w * 9 / 16
        setSizeRelativeLayout(rotateLayout, w, h)
        orientationListener = OrientationListener(this)
    }

    private fun setSizeRelativeLayout(view: View, w: Int, h: Int) {
        val params = view.layoutParams
        params.width = w
        params.height = h
        view.layoutParams = params
    }

    public override fun onStart() {
        orientationListener?.enable()
        super.onStart()
    }

    public override fun onStop() {
        orientationListener?.disable()
        super.onStop()
    }

    private inner class OrientationListener(context: Context?) : OrientationEventListener(context) {
        val rotation0 = 1
        val rotation90 = 2
        val rotation180 = 3
        val rotation270 = 4

        private var rotation = 0

        override fun onOrientationChanged(orientation: Int) {
            if ((orientation < 35 || orientation > 325) && rotation != rotation0) { // PORTRAIT
                rotation = rotation0
                rotateLayout.setAngle(0)
                val w = LScreenUtil.screenWidth
                val h = w * 9 / 16
                setSizeRelativeLayout(rotateLayout, w, h)
                LScreenUtil.toggleFullscreen(activity = this@SensorActivity, isFullScreen = false)
            } else if (orientation in 146..214 && rotation != rotation180) { // REVERSE PORTRAIT
                rotation = rotation180
            } else if (orientation in 56..124 && rotation != rotation270) { // REVERSE LANDSCAPE
                rotation = rotation270
                rotateLayout.setAngle(90)
                val w = LScreenUtil.screenWidth
                val h = LScreenUtil.getScreenHeightIncludeNavigationBar()
                setSizeRelativeLayout(view = rotateLayout, w = w, h = h)
                LScreenUtil.toggleFullscreen(activity = this@SensorActivity, isFullScreen = true)
            } else if (orientation in 236..304 && rotation != rotation90) { // LANDSCAPE
                rotation = rotation90
                rotateLayout.setAngle(-90)
                val w = LScreenUtil.screenWidth
                val h = LScreenUtil.getScreenHeightIncludeNavigationBar()
                setSizeRelativeLayout(rotateLayout, w, h)
                LScreenUtil.toggleFullscreen(activity = this@SensorActivity, isFullScreen = true)
            }
        }
    }
}
