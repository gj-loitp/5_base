package vn.loitp.a.func

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_func_menu.*
import vn.loitp.R
import vn.loitp.a.func.activityAndService.ActivityServiceCommunicateActivityFont
import vn.loitp.a.func.dragDrop.DragDropSampleActivityFont
import vn.loitp.a.func.fullScreen.FullScreenActivityFont
import vn.loitp.a.func.glide.GlideActivityFont
import vn.loitp.a.func.hashmap.HashMapActivityFont
import vn.loitp.a.func.idleTime.IdleTimeActivityFont
import vn.loitp.a.func.keyboard.KeyboardActivity
import vn.loitp.a.func.keyboardHeightProvider.KeyboardHeightProviderActivityFont
import vn.loitp.a.func.location.LocationActivity
import vn.loitp.a.func.noti.MenuNotificationActivityFont
import vn.loitp.a.func.processPhoenix.ProcessPhoenixActivityFont
import vn.loitp.a.func.pump.PumpActivityFont
import vn.loitp.a.func.recolor.RecolorActivityFont
import vn.loitp.a.func.sensor.SensorActivityFont
import vn.loitp.a.func.simpleFingerGestures.SimpleFingerGesturesActivityFont
import vn.loitp.a.func.theme.ThemeActivityFont
import vn.loitp.a.func.viewDragHelper.ViewDragHelperActivityFont
import vn.loitp.a.func.viewDragHelperSimple.ViewDragHelperSimpleActivity1Font
import vn.loitp.a.func.viewDragHelperSimple.ViewDragHelperSimpleActivityFont
import vn.loitp.a.func.wallpo.WallpoActivityFont

@LogTag("MenuFunctionActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuFunctionActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_func_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuFunctionActivityFont::class.java.simpleName
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
        btProcessPhoenix.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btSimpleFingerGesture -> launchActivity(SimpleFingerGesturesActivityFont::class.java)
            btHashMap -> launchActivity(HashMapActivityFont::class.java)
            btIdleTime -> launchActivity(IdleTimeActivityFont::class.java)
            btDragDropSample -> launchActivity(DragDropSampleActivityFont::class.java)
            btToggleFullScreen -> launchActivity(FullScreenActivityFont::class.java)
            btViewDragHelper -> launchActivity(ViewDragHelperActivityFont::class.java)
            btRecolor -> launchActivity(RecolorActivityFont::class.java)
            btActivityServiceComunicate -> launchActivity(ActivityServiceCommunicateActivityFont::class.java)
            btLocation -> launchActivity(LocationActivity::class.java)
            btNotification -> launchActivity(MenuNotificationActivityFont::class.java)
            btPump -> launchActivity(PumpActivityFont::class.java)
            btViewDragHelperSimple -> launchActivity(ViewDragHelperSimpleActivityFont::class.java)
            btViewDragHelperSimple1 -> launchActivity(ViewDragHelperSimpleActivity1Font::class.java)
            btSensor -> launchActivity(SensorActivityFont::class.java)
            btGlide -> launchActivity(GlideActivityFont::class.java)
            btKeyboard -> launchActivity(KeyboardActivity::class.java)
            btKeyboardHeightProvider -> launchActivity(KeyboardHeightProviderActivityFont::class.java)
            btTheme -> launchActivity(ThemeActivityFont::class.java)
            btWallpoActivity -> launchActivity(WallpoActivityFont::class.java)
            btProcessPhoenix -> launchActivity(ProcessPhoenixActivityFont::class.java)
        }
    }
}
