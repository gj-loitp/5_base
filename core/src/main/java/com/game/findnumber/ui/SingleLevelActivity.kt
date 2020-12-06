package com.game.findnumber.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.game.findnumber.adapter.LevelAdapter
import com.game.findnumber.model.Level
import kotlinx.android.synthetic.main.l_activity_find_number_single_level.*

@LogTag("SingleLevelActivity")
@IsFullScreen(true)
class SingleLevelActivity : BaseFontActivity() {

    private var levelAdapter = LevelAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_single_level
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupViews()
        setupData()
    }

    private fun setupViews() {
        levelAdapter.onClickRootView = { level, view ->
            LUIUtil.setOnClickListenerElastic(
                    view = view,
                    runnable = {
                        //TODO loitpp iplm
                    })
        }
        rvLevel.layoutManager = GridLayoutManager(this, 4)
        rvLevel.adapter = levelAdapter

        LUIUtil.setDelay(100) {
            tvLevels?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = tvLevels,
                    duration = 1000,
                    techniques = Techniques.ZoomInDown
            )

            ivBack?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = ivBack,
                    duration = 1000,
                    techniques = Techniques.ZoomInUp
            )

            ivPlay?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = ivPlay,
                    duration = 1000,
                    techniques = Techniques.ZoomInUp
            )

            ivSpiral?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = ivSpiral,
                    duration = 5000,
                    techniques = Techniques.RotateIn,
                    repeatCount = -1
            )
        }
        LUIUtil.setSafeOnClickListenerElastic(
                view = tvLevels
        )
        LUIUtil.setSafeOnClickListenerElastic(
                view = ivBack,
                runnable = {
                    onBackPressed()
                })
        LUIUtil.setSafeOnClickListenerElastic(
                view = ivPlay,
                runnable = {
                    //TODO loitp iplm
                })
    }

    private fun setupData() {
        val listLevel = ArrayList<Level>()
        for (i in 0 until 100) {
            val level = Level()
            level.name = "${i + 1}"

            if (i < 7) {
                level.status = Level.STATUS_LEVEL_WIN
            } else {
                level.status = Level.STATUS_LEVEL_OPEN
            }

            listLevel.add(element = level)
        }
        levelAdapter.setListLevel(listLevel = listLevel)

        LUIUtil.setDelay(mls = 100, runnable = {
            layoutLevel?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = layoutLevel,
                    duration = 1000,
                    techniques = Techniques.FadeInUp
            )
        })
    }
}
