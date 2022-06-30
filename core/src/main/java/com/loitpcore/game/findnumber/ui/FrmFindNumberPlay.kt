package com.loitpcore.game.findnumber.ui

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.daimajia.androidanimations.library.Techniques
import com.loitpcore.R
import com.loitpcore.animation.morphtransitions.MorphTransform
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.base.BaseFragment
import com.loitpcore.core.utilities.LAnimationUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.game.findnumber.dialog.FindNumberWinActivity
import com.loitpcore.game.findnumber.model.Level
import com.loitpcore.views.textview.autofit.LAutofitTextView
import kotlinx.android.synthetic.main.l_frm_find_number_play.*

@LogTag("FrmFindNumberPlay")
class FrmFindNumberPlay(
    val level: Level
) : BaseFragment() {

    private var numberTarget = 1
    private val listData = ArrayList<String>()

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_find_number_play
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupDataLevel()
        setupData()
    }

    private fun setupViews() {
        setupNumberTarget()
    }

    private fun setupNumberTarget() {
        tvNumberTarget.text = "$numberTarget"
        LAnimationUtil.play(view = tvNumberTarget, techniques = Techniques.Pulse)
    }

    private fun setupDataLevel() {
        logD("setupDataLevel " + BaseApplication.gson.toJson(level))
    }

    private fun setupData() {
        val mRows = level.row
        val mCols = level.col

        val size = mRows * mCols
        for (i in 0 until size) {
            listData.add("${i + 1}")
        }
        listData.shuffle()
        logD("listData " + BaseApplication.gson.toJson(listData))

        var layoutParams: ConstraintLayout.LayoutParams
        var id: Int
        val idArray = Array(mRows) {
            IntArray(mCols)
        }
        val constraintSet = ConstraintSet()

        var index = 0
        context?.let { c ->
            for (iRow in 0 until mRows) {
                for (iCol in 0 until mCols) {
                    val lAutofitTextView = LAutofitTextView(c)
                    layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintSet.MATCH_CONSTRAINT,
                        ConstraintSet.MATCH_CONSTRAINT
                    )
                    id = View.generateViewId()
                    idArray[iRow][iCol] = id
                    lAutofitTextView.id = id
                    lAutofitTextView.text = listData[index]
                    lAutofitTextView.rotation = level.rotate
                    lAutofitTextView.gravity = Gravity.CENTER
                    lAutofitTextView.setTextColor(Color.WHITE)
                    lAutofitTextView.setBackgroundResource(level.frame)
                    lAutofitTextView.isSingleLine = true
                    lAutofitTextView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, 100f)
                    lAutofitTextView.setMinTextSize(12)

                    LUIUtil.setSafeOnClickListenerElastic(
                        view = lAutofitTextView,
                        runnable = {
                            if (numberTarget == listData.size) {
                                winGame(lAutofitTextView)
                                return@setSafeOnClickListenerElastic
                            }
                            if (numberTarget.toString() == lAutofitTextView.text.toString()) {
                                lAutofitTextView.visibility = View.INVISIBLE
                                numberTarget++
                                setupNumberTarget()
                            }
                        }
                    )
                    layoutRootView.addView(lAutofitTextView, layoutParams)
                    index++
                }
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

    private fun winGame(view: View) {
        activity?.let { a ->
            val intent = Intent(context, FindNumberWinActivity::class.java)
            MorphTransform.addExtras(
                intent,
                ContextCompat.getColor(a, R.color.colorAccent),
                resources.getDimensionPixelSize(R.dimen.round_medium)
            )
            val options = ActivityOptions.makeSceneTransitionAnimation(
                a,
                view,
                getString(R.string.transition_morph)
            )
            startActivity(intent, options.toBundle())
        }
    }
}
