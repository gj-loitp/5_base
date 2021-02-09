package vn.loitp.app.activity.customviews.layout.relativepopupwindow

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.views.LToast
import kotlinx.android.synthetic.main.popup_card.view.*
import vn.loitp.app.R

class ExampleCardPopup internal constructor(context: Context?) : RelativePopupWindow() {

    override fun showOnAnchor(anchor: View, vertPos: Int, horizPos: Int, x: Int, y: Int, fitInScreen: Boolean) {
        super.showOnAnchor(anchor, vertPos, horizPos, x, y, fitInScreen)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            circularReveal(anchor = anchor)
//        }
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private fun circularReveal(anchor: View) {
//        val contentView = contentView
//        contentView.post {
//            val myLocation = IntArray(2)
//            val anchorLocation = IntArray(2)
//            contentView.getLocationOnScreen(myLocation)
//            anchor.getLocationOnScreen(anchorLocation)
//            val cx = anchorLocation[0] - myLocation[0] + anchor.width / 2
//            val cy = anchorLocation[1] - myLocation[1] + anchor.height / 2
//            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//            val dx = max(a = cx, b = contentView.measuredWidth - cx)
//            val dy = max(a = cy, b = contentView.measuredHeight - cy)
//            val finalRadius = hypot(x = dx.toDouble(), y = dy.toDouble()).toFloat()
//            val animator = ViewAnimationUtils.createCircularReveal(contentView, cx, cy, 0f, finalRadius)
//            animator.duration = 500
//            animator.start()
//        }
//    }

    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.popup_card, null)
        contentView = layout
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Disable default animation for circular reveal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animationStyle = 0
        }

        layout.ll.setOnClickListener {
            LToast.showShortInformation(msg = "On Click", isTopAnchor = true)
        }
    }
}
