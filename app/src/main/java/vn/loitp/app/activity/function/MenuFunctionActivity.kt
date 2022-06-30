package vn.loitp.app.activity.function

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_function_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.function.activityandservice.ActivityServiceComunicateActivity
import vn.loitp.app.activity.function.dragdropsample.DragDropSampleActivity
import vn.loitp.app.activity.function.fullscreen.FullScreenActivity
import vn.loitp.app.activity.function.glide.GlideActivity
import vn.loitp.app.activity.function.hashmap.HashMapActivity
import vn.loitp.app.activity.function.keyboard.KeyboardActivity
import vn.loitp.app.activity.function.keyboardheightprovider.KeyboardHeightProviderActivity
import vn.loitp.app.activity.function.location.LocationActivity
import vn.loitp.app.activity.function.notification.MenuNotificationActivity
import vn.loitp.app.activity.function.pump.PumpActivity
import vn.loitp.app.activity.function.recolor.RecolorActivity
import vn.loitp.app.activity.function.sensor.SensorActivity
import vn.loitp.app.activity.function.simplefingergestures.SimpleFingerGesturesActivity
import vn.loitp.app.activity.function.theme.ThemeActivity
import vn.loitp.app.activity.function.viewdraghelper.ViewDragHelperActivity
import vn.loitp.app.activity.function.viewdraghelpersimple.ViewDragHelperSimpleActivity
import vn.loitp.app.activity.function.viewdraghelpersimple.ViewDragHelperSimpleActivity1

@LogTag("MenuFunctionActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuFunctionActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_function_menu
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = false
            this.tvTitle?.text = MenuFunctionActivity::class.java.simpleName
        }
        btSimpleFingerGesture.setOnClickListener(this)
        btHashMap.setOnClickListener(this)
        btDragDropSample.setOnClickListener(this)
        btToggleFullScreen.setOnClickListener(this)
        btViewDragHelper.setOnClickListener(this)
        btRecolor.setOnClickListener(this)
        btActivityServiceComunicate.setOnClickListener(this)
        btLocation.setOnClickListener(this)
        btNotification.setOnClickListener(this)
        btPump.setOnClickListener(this)
        btViewDragHelperSimple.setOnClickListener(this)
        btViewDragHelperSimple1.setOnClickListener(this)
        btSensor.setOnClickListener(this)
        btGlide.setOnClickListener(this)
        btKeyboard.setOnClickListener(this)
        btKeyboardHeightProvider.setOnClickListener(this)
        btTheme.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent = when (v) {
            btSimpleFingerGesture -> Intent(this, SimpleFingerGesturesActivity::class.java)
            btHashMap -> Intent(this, HashMapActivity::class.java)
            btDragDropSample -> Intent(this, DragDropSampleActivity::class.java)
            btToggleFullScreen -> Intent(this, FullScreenActivity::class.java)
            btViewDragHelper -> Intent(this, ViewDragHelperActivity::class.java)
            btRecolor -> Intent(this, RecolorActivity::class.java)
            btActivityServiceComunicate -> Intent(
                this,
                ActivityServiceComunicateActivity::class.java
            )
            btLocation -> Intent(this, LocationActivity::class.java)
            btNotification -> Intent(this, MenuNotificationActivity::class.java)
            btPump -> Intent(this, PumpActivity::class.java)
            btViewDragHelperSimple -> Intent(this, ViewDragHelperSimpleActivity::class.java)
            btViewDragHelperSimple1 -> Intent(this, ViewDragHelperSimpleActivity1::class.java)
            btSensor -> Intent(this, SensorActivity::class.java)
            btGlide -> Intent(this, GlideActivity::class.java)
            btKeyboard -> Intent(this, KeyboardActivity::class.java)
            btKeyboardHeightProvider -> Intent(this, KeyboardHeightProviderActivity::class.java)
            btTheme -> Intent(this, ThemeActivity::class.java)
            else -> null
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
