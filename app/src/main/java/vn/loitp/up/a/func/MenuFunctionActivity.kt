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
import vn.loitp.databinding.AFuncMenuBinding
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
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
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
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btSimpleFingerGesture -> launchActivity(SimpleFingerGesturesActivityFont::class.java)
            binding.btHashMap -> launchActivity(HashMapActivityFont::class.java)
            binding.btIdleTime -> launchActivity(IdleTimeActivityFont::class.java)
            binding.btDragDropSample -> launchActivity(DragDropSampleActivityFont::class.java)
            binding.btToggleFullScreen -> launchActivity(FullScreenActivityFont::class.java)
            binding.btViewDragHelper -> launchActivity(ViewDragHelperActivity::class.java)
            binding.btRecolor -> launchActivity(RecolorActivityFont::class.java)
            binding.btActivityServiceComunicate -> launchActivity(
                ActivityServiceCommunicateActivityFont::class.java
            )
            binding.btLocation -> launchActivity(LocationActivity::class.java)
            binding.btNotification -> launchActivity(MenuNotificationActivityFont::class.java)
            binding.btPump -> launchActivity(PumpActivityFont::class.java)
            binding.btViewDragHelperSimple -> launchActivity(ViewDragHelperSimpleActivity::class.java)
            binding.btViewDragHelperSimple1 -> launchActivity(ViewDragHelperSimpleActivity1::class.java)
            binding.btSensor -> launchActivity(SensorActivityFont::class.java)
            binding.btGlide -> launchActivity(GlideActivityFont::class.java)
            binding.btKeyboard -> launchActivity(KeyboardActivity::class.java)
            binding.btKeyboardHeightProvider -> launchActivity(KeyboardHeightProviderActivityFont::class.java)
            binding.btTheme -> launchActivity(ThemeActivity::class.java)
            binding.btWallpoActivity -> launchActivity(WallpoActivity::class.java)
            binding.btProcessPhoenix -> launchActivity(ProcessPhoenixActivityFont::class.java)
        }
    }
}
