package vn.loitp.app.activity.function.simplefingergestures

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.function.simplefingergestures.SimpleFingerGestures
import com.function.simplefingergestures.SimpleFingerGestures.OnFingerGestureListener
import kotlinx.android.synthetic.main.activity_func_simple_finger_gestures.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_func_simple_finger_gestures)
class SimpleFingerGesturesActivity : BaseFontActivity() {
    private val mySfg = SimpleFingerGestures()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
//        val width = size.x
//        val height = size.y
        val sfg = SimpleFingerGestures()
        sfg.setDebug(true)
        sfg.consumeTouchEvents = true

        sfg.setOnFingerGestureListener(object : OnFingerGestureListener {
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
        iv.setOnTouchListener(sfg)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
