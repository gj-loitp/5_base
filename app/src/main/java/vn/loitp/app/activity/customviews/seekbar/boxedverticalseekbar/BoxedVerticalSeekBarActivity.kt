package vn.loitp.app.activity.customviews.seekbar.boxedverticalseekbar

import android.graphics.Color
import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.views.seekbar.boxedvertical.LBoxedVertical
import kotlinx.android.synthetic.main.activity_seekbar_boxed_vertical.*
import vn.loitp.app.R
import java.util.*

//https://github.com/alpbak/BoxedVerticalSeekBar?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6291

@LayoutId(R.layout.activity_seekbar_boxed_vertical)
class BoxedVerticalSeekBarActivity : BaseFontActivity() {
    private val stringList = ArrayList<String>()
    private val x: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        boxedVertical.setOnBoxedPointsChangeListener(object : LBoxedVertical.OnValuesChangeListener {
            override fun onPointsChanged(boxedPoints: LBoxedVertical, value: Int) {
                logD("onPointsChanged $value")
                stringList.add(index = 0, element = "onPointsChanged $value")
                print()
            }

            override fun onStartTrackingTouch(boxedPoints: LBoxedVertical) {
                logD("onStartTrackingTouch")
                stringList.add(index = 0, element = "onStartTrackingTouch")
                print()
            }

            override fun onStopTrackingTouch(boxedPoints: LBoxedVertical) {
                logD("onStopTrackingTouch")
                stringList.add(index = 0, element = "onStopTrackingTouch")
                print()
                boxedVertical.setBackgroundColor(Color.RED)
                boxedVertical.setProgressColor(Color.GREEN)
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
        textView?.text = x
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
