package vn.loitp.app.activity.function

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_function_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.function.activityandservice.ActivityServiceComunicateActivity
import vn.loitp.app.activity.function.downloadmanager.DownloadManagerActivity
import vn.loitp.app.activity.function.dragdropsample.DragDropSampleActivity
import vn.loitp.app.activity.function.fullscreen.FullScreenActivity
import vn.loitp.app.activity.function.glide.GlideActivity
import vn.loitp.app.activity.function.hashmap.HashMapActivity
import vn.loitp.app.activity.function.keyboard.KeyboardActivity
import vn.loitp.app.activity.function.location.LocationActivity
import vn.loitp.app.activity.function.notification.MenuNotificationActivity
import vn.loitp.app.activity.function.recolor.RecolorActivity
import vn.loitp.app.activity.function.sensor.SensorActivity
import vn.loitp.app.activity.function.simplefingergestures.SimpleFingerGesturesActivity
import vn.loitp.app.activity.function.viewdraghelper.ViewDragHelperActivity
import vn.loitp.app.activity.function.viewdraghelpersimple.ViewDragHelperSimpleActivity
import vn.loitp.app.activity.function.viewdraghelpersimple.ViewDragHelperSimpleActivity1

@LayoutId(R.layout.activity_function_menu)
@LogTag("MenuFunctionActivity")
class MenuFunctionActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btSimpleFingerGesture.setOnClickListener(this)
        btHashmap.setOnClickListener(this)
        btDragDropSample.setOnClickListener(this)
        btToggleFullScreen.setOnClickListener(this)
        btViewDragHelper.setOnClickListener(this)
        btRecolor.setOnClickListener(this)
        btActivityServiceComunicate.setOnClickListener(this)
        btLocation.setOnClickListener(this)
        btNotification.setOnClickListener(this)
        btViewDragHelperSimple.setOnClickListener(this)
        btViewDragHelperSimple1.setOnClickListener(this)
        btSensor.setOnClickListener(this)
        btGlide.setOnClickListener(this)
        btKeyboard.setOnClickListener(this)
        btDownloadManager.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btSimpleFingerGesture -> intent = Intent(activity, SimpleFingerGesturesActivity::class.java)
            btHashmap -> intent = Intent(activity, HashMapActivity::class.java)
            btDragDropSample -> intent = Intent(activity, DragDropSampleActivity::class.java)
            btToggleFullScreen -> intent = Intent(activity, FullScreenActivity::class.java)
            btViewDragHelper -> intent = Intent(activity, ViewDragHelperActivity::class.java)
            btRecolor -> intent = Intent(activity, RecolorActivity::class.java)
            btActivityServiceComunicate -> intent = Intent(activity, ActivityServiceComunicateActivity::class.java)
            btLocation -> intent = Intent(activity, LocationActivity::class.java)
            btNotification -> intent = Intent(activity, MenuNotificationActivity::class.java)
            btViewDragHelperSimple -> intent = Intent(activity, ViewDragHelperSimpleActivity::class.java)
            btViewDragHelperSimple1 -> intent = Intent(activity, ViewDragHelperSimpleActivity1::class.java)
            btSensor -> intent = Intent(activity, SensorActivity::class.java)
            btGlide -> intent = Intent(activity, GlideActivity::class.java)
            btKeyboard -> intent = Intent(activity, KeyboardActivity::class.java)
            btDownloadManager -> intent = Intent(activity, DownloadManagerActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
