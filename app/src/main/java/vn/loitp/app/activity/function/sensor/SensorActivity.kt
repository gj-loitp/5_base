package vn.loitp.app.activity.function.sensor

import android.content.Context
import android.os.Bundle
import android.view.OrientationEventListener
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_func_sensor.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants.Companion.URL_IMG

@LayoutId(R.layout.activity_func_sensor)
@LogTag("SensorActivity")
@IsFullScreen(false)
class SensorActivity : BaseFontActivity() {
    private var orientationListener: OrientationListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val ROTATION_O = 1
        val ROTATION_90 = 2
        val ROTATION_180 = 3
        val ROTATION_270 = 4

        private var rotation = 0

        override fun onOrientationChanged(orientation: Int) {
            if ((orientation < 35 || orientation > 325) && rotation != ROTATION_O) { // PORTRAIT
                rotation = ROTATION_O
                rotateLayout.angle = 0
                val w = LScreenUtil.screenWidth
                val h = w * 9 / 16
                setSizeRelativeLayout(rotateLayout, w, h)
                LScreenUtil.toggleFullscreen(activity = this@SensorActivity, isFullScreen = false)
            } else if (orientation in 146..214 && rotation != ROTATION_180) { // REVERSE PORTRAIT
                rotation = ROTATION_180
            } else if (orientation in 56..124 && rotation != ROTATION_270) { // REVERSE LANDSCAPE
                rotation = ROTATION_270
                rotateLayout.angle = 90
                val w = LScreenUtil.screenWidth
                val h = LScreenUtil.getScreenHeightIncludeNavigationBar()
                setSizeRelativeLayout(view = rotateLayout, w = w, h = h)
                LScreenUtil.toggleFullscreen(activity = this@SensorActivity, isFullScreen = true)
            } else if (orientation in 236..304 && rotation != ROTATION_90) { //LANDSCAPE
                rotation = ROTATION_90
                rotateLayout.angle = -90
                val w = LScreenUtil.screenWidth
                val h = LScreenUtil.getScreenHeightIncludeNavigationBar()
                setSizeRelativeLayout(rotateLayout, w, h)
                LScreenUtil.toggleFullscreen(activity = this@SensorActivity, isFullScreen = true)
            }
        }
    }
}
