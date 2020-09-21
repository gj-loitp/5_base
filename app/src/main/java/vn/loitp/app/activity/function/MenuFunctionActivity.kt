package vn.loitp.app.activity.function

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
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
@IsFullScreen(false)
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

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btSimpleFingerGesture -> intent = Intent(this, SimpleFingerGesturesActivity::class.java)
            btHashmap -> intent = Intent(this, HashMapActivity::class.java)
            btDragDropSample -> intent = Intent(this, DragDropSampleActivity::class.java)
            btToggleFullScreen -> intent = Intent(this, FullScreenActivity::class.java)
            btViewDragHelper -> intent = Intent(this, ViewDragHelperActivity::class.java)
            btRecolor -> intent = Intent(this, RecolorActivity::class.java)
            btActivityServiceComunicate -> intent = Intent(this, ActivityServiceComunicateActivity::class.java)
            btLocation -> intent = Intent(this, LocationActivity::class.java)
            btNotification -> intent = Intent(this, MenuNotificationActivity::class.java)
            btViewDragHelperSimple -> intent = Intent(this, ViewDragHelperSimpleActivity::class.java)
            btViewDragHelperSimple1 -> intent = Intent(this, ViewDragHelperSimpleActivity1::class.java)
            btSensor -> intent = Intent(this, SensorActivity::class.java)
            btGlide -> intent = Intent(this, GlideActivity::class.java)
            btKeyboard -> intent = Intent(this, KeyboardActivity::class.java)
            btDownloadManager -> intent = Intent(this, DownloadManagerActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
