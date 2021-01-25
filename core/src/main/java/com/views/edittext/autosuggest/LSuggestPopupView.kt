package com.views.edittext.autosuggest

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import com.R
import com.core.utilities.LUIUtil
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.utils.util.ConvertUtils
import kotlin.math.hypot
import kotlin.math.max

class LSuggestPopupView(val context: Context, val withEffect: Boolean, val callback: Callback?) : RelativePopupWindow() {
    private val logTag = javaClass.simpleName
    private var ll: LinearLayout
    private var sv: ScrollView

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
        sv = layout.findViewById(R.id.sv)
        if (withEffect) {
            LUIUtil.setPullLikeIOSVertical(sv)
        }
    }

    interface Callback {
        fun onClick(s: String)
    }

    override fun showOnAnchor(anchor: View, vertPos: Int, horizPos: Int, x: Int, y: Int, fitInScreen: Boolean) {
        super.showOnAnchor(anchor, vertPos, horizPos, x, y, fitInScreen)
        if (withEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        val padding = ConvertUtils.dp2px(10f)
        ll.removeAllViews()
        for (s in stringList) {
            val button = Button(context)
            button.setPadding(padding, padding, padding, padding)
            button.isAllCaps = false
            button.gravity = Gravity.START
            button.isSingleLine = true
            //button.setBackgroundColor(Color.TRANSPARENT)
            button.text = s
            button.setTextColor(Color.BLACK)
            LUIUtil.setTextSize(textView = button, size = context.resources.getDimension(R.dimen.text_medium))
            LUIUtil.setRipple(view = button)
            button.setOnClickListener {
                callback?.onClick(s)
            }
            ll.addView(button)
        }
        sv.scrollTo(0, 0)
    }
}
