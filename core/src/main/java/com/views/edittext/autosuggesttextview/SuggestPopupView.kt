package com.views.edittext.autosuggesttextview

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.views.layout.relativepopupwindow.RelativePopupWindow
import loitp.core.R
import kotlin.math.hypot
import kotlin.math.max

class SuggestPopupView(val context: Context, val callback: Callback?) : RelativePopupWindow() {
    private val TAG = javaClass.simpleName;
    private lateinit var ll: LinearLayout

    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.view_auto_suggest_edittext_popup, null)
        contentView = layout
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = false
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Disable default animation for circular reveal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animationStyle = 0
        }

        ll = layout.findViewById(R.id.ll)
    }

    override fun showOnAnchor(anchor: View, vertPos: Int, horizPos: Int, x: Int, y: Int, fitInScreen: Boolean) {
        super.showOnAnchor(anchor, vertPos, horizPos, x, y, fitInScreen)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circularReveal(anchor)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun circularReveal(anchor: View) {
        val contentView = contentView
        contentView.post {
            val myLocation = IntArray(2)
            val anchorLocation = IntArray(2)
            contentView.getLocationOnScreen(myLocation)
            anchor.getLocationOnScreen(anchorLocation)
            val cx = anchorLocation[0] - myLocation[0] + anchor.width / 2
            val cy = anchorLocation[1] - myLocation[1] + anchor.height / 2

            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val dx = max(cx, contentView.measuredWidth - cx)
            val dy = max(cy, contentView.measuredHeight - cy)
            val finalRadius = hypot(dx.toDouble(), dy.toDouble()).toFloat()
            val animator = ViewAnimationUtils.createCircularReveal(contentView, cx, cy, 0f, finalRadius)
            animator.duration = 500
            animator.start()
        }
    }

    fun setStringList(stringList: ArrayList<String>) {
        for (s in stringList) {
            val tv = Button(context)
            tv.setBackgroundColor(Color.TRANSPARENT)
            tv.text = s
            tv.setTextColor(Color.BLACK)
            LUIUtil.setTextSize(tv, TypedValue.COMPLEX_UNIT_DIP, 16)
            tv.setOnClickListener {
                LLog.d(TAG, "onClick $s")
                callback?.onClick(s)
            }
            ll.addView(tv)
        }
    }

    interface Callback {
        fun onClick(s: String)
    }
}