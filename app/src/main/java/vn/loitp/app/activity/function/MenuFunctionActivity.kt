package vn.loitp.app.activity.function

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_function_menu.*

import vn.loitp.app.R
import vn.loitp.app.activity.function.activityandservice.ActivityServiceComunicateActivity
import vn.loitp.app.activity.function.downloadmanager.DownloadManagerActivity
import vn.loitp.app.activity.function.dragdropsample.DragDropSampleActivity
import vn.loitp.app.activity.function.fullscreen.FullScreenActivity
import vn.loitp.app.activity.function.gesto.GestoActivity
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

class MenuFunctionActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false

        findViewById<View>(R.id.bt_gesto).setOnClickListener(this)
        findViewById<View>(R.id.bt_simple_finger_gesture).setOnClickListener(this)
        findViewById<View>(R.id.bt_hashmap).setOnClickListener(this)
        findViewById<View>(R.id.bt_drag_drop_sample).setOnClickListener(this)
        findViewById<View>(R.id.btToggleFullScreen).setOnClickListener(this)
        findViewById<View>(R.id.bt_view_drag_helper).setOnClickListener(this)
        findViewById<View>(R.id.bt_recolor).setOnClickListener(this)
        findViewById<View>(R.id.bt_activity_service_comunicate).setOnClickListener(this)
        findViewById<View>(R.id.bt_location).setOnClickListener(this)
        findViewById<View>(R.id.bt_notification).setOnClickListener(this)
        findViewById<View>(R.id.bt_view_drag_helper_simple).setOnClickListener(this)
        findViewById<View>(R.id.bt_view_drag_helper_simple_1).setOnClickListener(this)
        findViewById<View>(R.id.bt_sensor).setOnClickListener(this)
        findViewById<View>(R.id.bt_glide).setOnClickListener(this)
        findViewById<View>(R.id.bt_keyboard).setOnClickListener(this)
        btDownloadManager.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_function_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_gesto -> intent = Intent(activity, GestoActivity::class.java)
            R.id.bt_simple_finger_gesture -> intent = Intent(activity, SimpleFingerGesturesActivity::class.java)
            R.id.bt_hashmap -> intent = Intent(activity, HashMapActivity::class.java)
            R.id.bt_drag_drop_sample -> intent = Intent(activity, DragDropSampleActivity::class.java)
            R.id.btToggleFullScreen -> intent = Intent(activity, FullScreenActivity::class.java)
            R.id.bt_view_drag_helper -> intent = Intent(activity, ViewDragHelperActivity::class.java)
            R.id.bt_recolor -> intent = Intent(activity, RecolorActivity::class.java)
            R.id.bt_activity_service_comunicate -> intent = Intent(activity, ActivityServiceComunicateActivity::class.java)
            R.id.bt_location -> intent = Intent(activity, LocationActivity::class.java)
            R.id.bt_notification -> intent = Intent(activity, MenuNotificationActivity::class.java)
            R.id.bt_view_drag_helper_simple -> intent = Intent(activity, ViewDragHelperSimpleActivity::class.java)
            R.id.bt_view_drag_helper_simple_1 -> intent = Intent(activity, ViewDragHelperSimpleActivity1::class.java)
            R.id.bt_sensor -> intent = Intent(activity, SensorActivity::class.java)
            R.id.bt_glide -> intent = Intent(activity, GlideActivity::class.java)
            R.id.bt_keyboard -> intent = Intent(activity, KeyboardActivity::class.java)
            R.id.btDownloadManager -> intent = Intent(activity, DownloadManagerActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
