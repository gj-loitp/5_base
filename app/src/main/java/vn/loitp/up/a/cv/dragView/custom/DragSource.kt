package vn.loitp.up.a.cv.dragView.custom

import android.content.Context
import android.util.AttributeSet
import com.tuanhav95.drag.DragView
import com.tuanhav95.drag.utils.inflate
import com.tuanhav95.drag.utils.reWidth
import kotlinx.android.synthetic.main.l_drag_view_top.view.*
import vn.loitp.R

class DragSource @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : DragView(context, attrs, defStyleAttr) {

    private var mWidthWhenMax = 0
    private var mWidthWhenMiddle = 0
    private var mWidthWhenMin = 0

    init {
        getFrameFirst().addView(inflate(R.layout.l_drag_view_top))
        getFrameSecond().addView(inflate(R.layout.l_drag_view_bottom))
    }

    override fun initFrame() {
        mWidthWhenMax = width
        mWidthWhenMiddle = (width - mPercentWhenMiddle * mMarginEdgeWhenMin).toInt()
        mWidthWhenMin = mHeightWhenMin * 22 / 9
        super.initFrame()
    }

    override fun refreshFrameFirst() {
        super.refreshFrameFirst()

        val width = if (mCurrentPercent < mPercentWhenMiddle) {
            (mWidthWhenMax - (mWidthWhenMax - mWidthWhenMiddle) * mCurrentPercent)
        } else {
            (mWidthWhenMiddle - (mWidthWhenMiddle - mWidthWhenMin) * (mCurrentPercent - mPercentWhenMiddle) / (1 - mPercentWhenMiddle))
        }

        frameTop.reWidth(width.toInt())
    }
}
