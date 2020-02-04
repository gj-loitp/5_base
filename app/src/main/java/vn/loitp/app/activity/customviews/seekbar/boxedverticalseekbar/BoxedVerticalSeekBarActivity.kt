package vn.loitp.app.activity.customviews.seekbar.boxedverticalseekbar

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.views.seekbar.boxedvertical.LBoxedVertical
import vn.loitp.app.R
import java.util.*

//https://github.com/alpbak/BoxedVerticalSeekBar?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6291
class BoxedVerticalSeekBarActivity : BaseFontActivity() {
    private var tv: TextView? = null
    private val stringList = ArrayList<String>()

    private val x: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bv = findViewById<LBoxedVertical>(R.id.boxed_vertical)
        tv = findViewById(R.id.tv)

        bv.setOnBoxedPointsChangeListener(object : LBoxedVertical.OnValuesChangeListener {
            override fun onPointsChanged(boxedPoints: LBoxedVertical, value: Int) {
                LLog.d(TAG, "onPointsChanged $value")
                stringList.add(0, "onPointsChanged $value")
                print()
            }

            override fun onStartTrackingTouch(boxedPoints: LBoxedVertical) {
                LLog.d(TAG, "onStartTrackingTouch")
                stringList.add(0, "onStartTrackingTouch")
                print()
            }

            override fun onStopTrackingTouch(boxedPoints: LBoxedVertical) {
                LLog.d(TAG, "onStopTrackingTouch")
                stringList.add(0, "onStopTrackingTouch")
                print()
                bv.setBackgroundColor(Color.RED)
                bv.setProgressColor(Color.GREEN)
            }
        })
    }

    private fun print() {
        if (stringList.size > 30) {
            stringList.removeAt(stringList.size - 1)
        }
        var x = ""
        for (s in stringList) {
            x += "\n" + s
        }
        tv?.text = x
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_boxed_vertical_seekbar
    }
}
