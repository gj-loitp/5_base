package vn.loitp.app.activity.customviews.seekbar.boxedverticalseekbar

import abak.tr.com.boxedverticalseekbar.BoxedVertical
import android.graphics.Color
import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_seekbar_boxed_vertical.*
import vn.loitp.app.R
import java.util.* // ktlint-disable no-wildcard-imports

// https://github.com/alpbak/BoxedVerticalSeekBar?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6291
@LogTag("BoxedVerticalSeekBarActivity")
@IsFullScreen(false)
class BoxedVerticalSeekBarActivity : BaseFontActivity() {
    private val stringList = ArrayList<String>()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_seekbar_boxed_vertical
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        boxedVertical.setOnBoxedPointsChangeListener(object : BoxedVertical.OnValuesChangeListener {
            override fun onPointsChanged(boxedPoints: BoxedVertical, value: Int) {
                logD("onPointsChanged $value")
                stringList.add(index = 0, element = "onPointsChanged $value")
                print()
            }

            override fun onStartTrackingTouch(boxedPoints: BoxedVertical) {
                logD("onStartTrackingTouch")
                stringList.add(index = 0, element = "onStartTrackingTouch")
                print()
            }

            override fun onStopTrackingTouch(boxedPoints: BoxedVertical) {
                logD("onStopTrackingTouch")
                stringList.add(index = 0, element = "onStopTrackingTouch")
                print()
                boxedVertical.setBackgroundColor(Color.RED)
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
}
