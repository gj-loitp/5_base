package vn.loitp.up.a.func

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AFuncMenuBinding
import vn.loitp.up.a.func.activityAndService.ActivityServiceCommunicateActivity
import vn.loitp.up.a.func.dragDrop.DragDropSampleActivity
import vn.loitp.up.a.func.fullScreen.FullScreenActivity
import vn.loitp.up.a.func.glide.GlideActivity
import vn.loitp.up.a.func.hashmap.HashMapActivity
import vn.loitp.up.a.func.idleTime.IdleTimeActivity
import vn.loitp.up.a.func.keyboard.KeyboardActivity
import vn.loitp.up.a.func.keyboardHeightProvider.KeyboardHeightProviderActivity
import vn.loitp.up.a.func.keyboardVisibility.KeyboardVisibilityActivity
import vn.loitp.up.a.func.location.LocationActivity
import vn.loitp.up.a.func.math.MathActivity
import vn.loitp.up.a.func.noti.MenuNotificationActivity
import vn.loitp.up.a.func.processPhoenix.ProcessPhoenixActivity
import vn.loitp.up.a.func.pump.PumpActivity
import vn.loitp.up.a.func.recolor.RecolorActivity
import vn.loitp.up.a.func.sensor.SensorActivity
import vn.loitp.up.a.func.simpleFingerGestures.SimpleFingerGesturesActivity
import vn.loitp.up.a.func.theme.ThemeActivity
import vn.loitp.up.a.func.viewDragHelper.ViewDragHelperActivity
import vn.loitp.up.a.func.viewDragHelperSimple.ViewDragHelperSimpleActivity
import vn.loitp.up.a.func.viewDragHelperSimple.ViewDragHelperSimpleActivity1
import vn.loitp.up.a.func.wallpo.WallpoActivity

@LogTag("MenuFunctionActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuFunctionActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var binding: AFuncMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFuncMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuFunctionActivity::class.java.simpleName
        }
        binding.btSimpleFingerGesture.setOnClickListener(this)
        binding.btHashMap.setOnClickListener(this)
        binding.btIdleTime.setOnClickListener(this)
        binding.btDragDropSample.setOnClickListener(this)
        binding.btToggleFullScreen.setOnClickListener(this)
        binding.btViewDragHelper.setOnClickListener(this)
        binding.btRecolor.setOnClickListener(this)
        binding.btActivityServiceComunicate.setOnClickListener(this)
        binding.btLocation.setOnClickListener(this)
        binding.btMath.setOnClickListener(this)
        binding.btNotification.setOnClickListener(this)
        binding.btPump.setOnClickListener(this)
        binding.btViewDragHelperSimple.setOnClickListener(this)
        binding.btViewDragHelperSimple1.setOnClickListener(this)
        binding.btSensor.setOnClickListener(this)
        binding.btGlide.setOnClickListener(this)
        binding.btKeyboard.setOnClickListener(this)
        binding.btKeyboardHeightProvider.setOnClickListener(this)
        binding.btTheme.setOnClickListener(this)
        binding.btWallpoActivity.setOnClickListener(this)
        binding.btProcessPhoenix.setOnClickListener(this)
        binding.btKeyboardVisibility.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btSimpleFingerGesture -> launchActivity(SimpleFingerGesturesActivity::class.java)
            binding.btHashMap -> launchActivity(HashMapActivity::class.java)
            binding.btIdleTime -> launchActivity(IdleTimeActivity::class.java)
            binding.btDragDropSample -> launchActivity(DragDropSampleActivity::class.java)
            binding.btToggleFullScreen -> launchActivity(FullScreenActivity::class.java)
            binding.btViewDragHelper -> launchActivity(ViewDragHelperActivity::class.java)
            binding.btRecolor -> launchActivity(RecolorActivity::class.java)
            binding.btActivityServiceComunicate -> launchActivity(
                ActivityServiceCommunicateActivity::class.java
            )
            binding.btLocation -> launchActivity(LocationActivity::class.java)
            binding.btMath -> launchActivity(MathActivity::class.java)
            binding.btNotification -> launchActivity(MenuNotificationActivity::class.java)
            binding.btPump -> launchActivity(PumpActivity::class.java)
            binding.btViewDragHelperSimple -> launchActivity(ViewDragHelperSimpleActivity::class.java)
            binding.btViewDragHelperSimple1 -> launchActivity(ViewDragHelperSimpleActivity1::class.java)
            binding.btSensor -> launchActivity(SensorActivity::class.java)
            binding.btGlide -> launchActivity(GlideActivity::class.java)
            binding.btKeyboard -> launchActivity(KeyboardActivity::class.java)
            binding.btKeyboardHeightProvider -> launchActivity(KeyboardHeightProviderActivity::class.java)
            binding.btTheme -> launchActivity(ThemeActivity::class.java)
            binding.btWallpoActivity -> launchActivity(WallpoActivity::class.java)
            binding.btProcessPhoenix -> launchActivity(ProcessPhoenixActivity::class.java)
            binding.btKeyboardVisibility -> launchActivity(KeyboardVisibilityActivity::class.java)
        }
    }
}
