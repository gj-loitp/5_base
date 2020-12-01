package vn.loitp.app.activity.function.simplefingergestures

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.function.simplefingergestures.SimpleFingerGestures
import kotlinx.android.synthetic.main.activity_func_simple_finger_gestures.*
import vn.loitp.app.R

@LogTag("SimpleFingerGesturesActivity")
@IsFullScreen(false)
class SimpleFingerGesturesActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_simple_finger_gestures
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val simpleFingerGestures = SimpleFingerGestures()
        simpleFingerGestures.consumeTouchEvents = true

        simpleFingerGestures.setOnFingerGestureListener(object : SimpleFingerGestures.OnFingerGestureListener {
            override fun onSwipeUp(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean {
                textView.text = "You swiped $fingers fingers  up $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onSwipeDown(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean {
                textView.text = "You swiped $fingers fingers  down $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onSwipeLeft(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean {
                textView.text = "You swiped $fingers fingers  left $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onSwipeRight(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean {
                textView.text = "You swiped $fingers fingers  right $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onPinch(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean {
                textView.text = "You pinched $fingers fingers $gestureDuration milliseconds $gestureDistance pixels far"
                return false
            }

            override fun onUnpinch(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean {
                textView.text = "You unpinched " + fingers + "fingers" + gestureDuration + " milliseconds " + gestureDistance + " pixels far"
                return false
            }

            override fun onDoubleTap(fingers: Int): Boolean {
                textView.text = "You double tapped"
                return false
            }
        })
        iv.setOnTouchListener(simpleFingerGestures)
    }
}
