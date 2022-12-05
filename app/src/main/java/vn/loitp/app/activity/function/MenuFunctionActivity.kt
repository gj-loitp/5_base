package vn.loitp.app.activity.function

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_function.*
import vn.loitp.app.R
import vn.loitp.app.activity.function.activityAndService.ActivityServiceCommunicateActivity
import vn.loitp.app.activity.function.dragDropSample.DragDropSampleActivity
import vn.loitp.app.activity.function.fullScreen.FullScreenActivity
import vn.loitp.app.activity.function.glide.GlideActivity
import vn.loitp.app.activity.function.hashmap.HashMapActivity
import vn.loitp.app.activity.function.idleTime.IdleTimeActivity
import vn.loitp.app.activity.function.keyboard.KeyboardActivity
import vn.loitp.app.activity.function.keyboardHeightProvider.KeyboardHeightProviderActivity
import vn.loitp.app.activity.function.location.LocationActivity
import vn.loitp.app.activity.function.notification.MenuNotificationActivity
import vn.loitp.app.activity.function.pump.PumpActivity
import vn.loitp.app.activity.function.recolor.RecolorActivity
import vn.loitp.app.activity.function.sensor.SensorActivity
import vn.loitp.app.activity.function.simpleFingerGestures.SimpleFingerGesturesActivity
import vn.loitp.app.activity.function.theme.ThemeActivity
import vn.loitp.app.activity.function.viewDragHelper.ViewDragHelperActivity
import vn.loitp.app.activity.function.viewDragHelperSimple.ViewDragHelperSimpleActivity
import vn.loitp.app.activity.function.viewDragHelperSimple.ViewDragHelperSimpleActivity1
import vn.loitp.app.activity.function.wallpo.WallpoActivity

@LogTag("MenuFunctionActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuFunctionActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_function
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = false
            this.tvTitle?.text = MenuFunctionActivity::class.java.simpleName
        }
        btSimpleFingerGesture.setOnClickListener(this)
        btHashMap.setOnClickListener(this)
        btIdleTime.setOnClickListener(this)
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
        btWallpoActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btSimpleFingerGesture -> launchActivity(SimpleFingerGesturesActivity::class.java)
            btHashMap -> launchActivity(HashMapActivity::class.java)
            btIdleTime -> launchActivity(IdleTimeActivity::class.java)
            btDragDropSample -> launchActivity(DragDropSampleActivity::class.java)
            btToggleFullScreen -> launchActivity(FullScreenActivity::class.java)
            btViewDragHelper -> launchActivity(ViewDragHelperActivity::class.java)
            btRecolor -> launchActivity(RecolorActivity::class.java)
            btActivityServiceComunicate -> launchActivity(ActivityServiceCommunicateActivity::class.java)
            btLocation -> launchActivity(LocationActivity::class.java)
            btNotification -> launchActivity(MenuNotificationActivity::class.java)
            btPump -> launchActivity(PumpActivity::class.java)
            btViewDragHelperSimple -> launchActivity(ViewDragHelperSimpleActivity::class.java)
            btViewDragHelperSimple1 -> launchActivity(ViewDragHelperSimpleActivity1::class.java)
            btSensor -> launchActivity(SensorActivity::class.java)
            btGlide -> launchActivity(GlideActivity::class.java)
            btKeyboard -> launchActivity(KeyboardActivity::class.java)
            btKeyboardHeightProvider -> launchActivity(KeyboardHeightProviderActivity::class.java)
            btTheme -> launchActivity(ThemeActivity::class.java)
            btWallpoActivity -> launchActivity(WallpoActivity::class.java)
        }
    }
}
