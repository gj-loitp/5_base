package vn.loitp.a.func

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_func_menu.*
import vn.loitp.R
import vn.loitp.a.func.activityAndService.ActivityServiceCommunicateActivity
import vn.loitp.a.func.dragDrop.DragDropSampleActivity
import vn.loitp.a.func.fullScreen.FullScreenActivity
import vn.loitp.a.func.glide.GlideActivity
import vn.loitp.a.func.hashmap.HashMapActivity
import vn.loitp.a.func.idleTime.IdleTimeActivity
import vn.loitp.a.func.keyboard.KeyboardActivity
import vn.loitp.a.func.keyboardHeightProvider.KeyboardHeightProviderActivity
import vn.loitp.a.func.location.LocationActivity
import vn.loitp.a.func.noti.MenuNotificationActivity
import vn.loitp.a.func.pump.PumpActivity
import vn.loitp.a.func.recolor.RecolorActivity
import vn.loitp.a.func.sensor.SensorActivity
import vn.loitp.app.a.func.simpleFingerGestures.SimpleFingerGesturesActivity
import vn.loitp.app.a.func.theme.ThemeActivity
import vn.loitp.app.a.func.viewDragHelper.ViewDragHelperActivity
import vn.loitp.app.a.func.viewDragHelperSimple.ViewDragHelperSimpleActivity
import vn.loitp.app.a.func.viewDragHelperSimple.ViewDragHelperSimpleActivity1
import vn.loitp.app.a.func.wallpo.WallpoActivity

@LogTag("MenuFunctionActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuFunctionActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_func_menu
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
