package vn.loitp.up.a.func.simpleFingerGestures

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.func.simpleFingerGestures.SimpleFingerGestures
import vn.loitp.R
import vn.loitp.databinding.AFuncSimpleFingerGesturesBinding

@LogTag("SimpleFingerGesturesActivity")
@IsFullScreen(false)
class SimpleFingerGesturesActivity : BaseActivityFont() {

    private lateinit var binding: AFuncSimpleFingerGesturesBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFuncSimpleFingerGesturesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = SimpleFingerGesturesActivity::class.java.simpleName
        }
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val simpleFingerGestures = SimpleFingerGestures()
        simpleFingerGestures.consumeTouchEvents = true

        simpleFingerGestures.setOnFingerGestureListener(object :
            SimpleFingerGestures.OnFingerGestureListener {
            override fun onSwipeUp(
                fingers: Int,
                gestureDuration: Long,
                gestureDistance: Double
            ): Boolean {
                binding.textView.text =
                    "You swiped $fingers fingers  up $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onSwipeDown(
                fingers: Int,
                gestureDuration: Long,
                gestureDistance: Double
            ): Boolean {
                binding.textView.text =
                    "You swiped $fingers fingers  down $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onSwipeLeft(
                fingers: Int,
                gestureDuration: Long,
                gestureDistance: Double
            ): Boolean {
                binding.textView.text =
                    "You swiped $fingers fingers  left $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onSwipeRight(
                fingers: Int,
                gestureDuration: Long,
                gestureDistance: Double
            ): Boolean {
                binding.textView.text =
                    "You swiped $fingers fingers  right $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onPinch(
                fingers: Int,
                gestureDuration: Long,
                gestureDistance: Double
            ): Boolean {
                binding.textView.text =
                    "You pinched $fingers fingers $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onUnpinch(
                fingers: Int,
                gestureDuration: Long,
                gestureDistance: Double
            ): Boolean {
                binding.textView.text =
                    "You unpinched " + fingers + "fingers" + gestureDuration + " milliseconds " + gestureDistance + " pixels far"
                return false
            }

            override fun onDoubleTap(fingers: Int): Boolean {
                binding.textView.text = "You double tapped"
                return false
            }
        })
        binding.iv.setOnTouchListener(simpleFingerGestures)
    }
}
