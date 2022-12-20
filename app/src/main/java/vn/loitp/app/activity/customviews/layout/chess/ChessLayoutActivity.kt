package vn.loitp.app.activity.customviews.layout.chess

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_chess_layout.*
import vn.loitp.app.R

@LogTag("ChessLayoutActivity")
@IsFullScreen(false)
class ChessLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_chess_layout
    }

    private var mRows = 10
    private var mCols = 10
    private val listData = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ChessLayoutActivity::class.java.simpleName
        }
        val size = mRows * mCols
        for (i in 0..size) {
            listData.add("${i + 1}")
        }
        listData.shuffle()

        val color1 = LAppResource.getColor(R.color.green)
        val color2 = LAppResource.getColor(R.color.orange)
        var layoutParams: ConstraintLayout.LayoutParams
        var id: Int
        val idArray = Array(mRows) {
            IntArray(mCols)
        }
        val constraintSet = ConstraintSet()

        for (iRow in 0 until mRows) {
            for (iCol in 0 until mCols) {

                val textView = TextView(this)
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintSet.MATCH_CONSTRAINT,
                    ConstraintSet.MATCH_CONSTRAINT
                )
                id = View.generateViewId()
                idArray[iRow][iCol] = id
                textView.id = id
                textView.text = listData[iRow * 10 + iCol]
                textView.rotation = 45f
                textView.gravity = Gravity.CENTER
                textView.setTextColor(Color.WHITE)
                textView.setBackgroundColor(if ((iRow + iCol) % 2 == 0) color1 else color2)

                LUIUtil.setSafeOnClickListenerElastic(
                    view = textView,
                    runnable = {
                        showShortInformation("${textView.text}")
                    }
                )
                layoutRootView.addView(textView, layoutParams)
            }
        }

        constraintSet.clone(layoutRootView)
        val gridFrameId = R.id.gridFrame
        constraintSet.setDimensionRatio(gridFrameId, "$mCols:$mRows")
        for (iRow in 0 until mRows) {
            for (iCol in 0 until mCols) {
                id = idArray[iRow][iCol]
                constraintSet.setDimensionRatio(id, "1:1")
                if (iRow == 0) {
                    constraintSet.connect(id, ConstraintSet.TOP, gridFrameId, ConstraintSet.TOP)
                } else {
                    constraintSet.connect(
                        id,
                        ConstraintSet.TOP,
                        idArray[iRow - 1][0],
                        ConstraintSet.BOTTOM
                    )
                }
            }
            constraintSet.createHorizontalChain(
                gridFrameId, ConstraintSet.LEFT,
                gridFrameId, ConstraintSet.RIGHT,
                idArray[iRow], null, ConstraintSet.CHAIN_PACKED
            )
        }
        constraintSet.applyTo(layoutRootView)
    }
}
