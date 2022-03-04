package com.game.findnumber.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LDialogUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.game.findnumber.adapter.LevelAdapter
import com.game.findnumber.model.Level
import com.game.findnumber.viewmodel.FindNumberViewModel
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.l_activity_find_number_single_level.*

@LogTag("SingleLevelActivity")
@IsFullScreen(true)
@IsSwipeActivity(true)
class SingleLevelActivity : BaseFontActivity() {

    private var activityCircularReveal: CircularReveal? = null
    private var levelAdapter = LevelAdapter()
    private var findNumberViewModel: FindNumberViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_single_level
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        activityCircularReveal = CircularReveal(rootView)
        activityCircularReveal?.onActivityCreate(intent)

        setupViews()
        setupViewModels()

        findNumberViewModel?.getListLevelSingle()
    }

    override fun onBackPressed() {
        activityCircularReveal?.unRevealActivity(this)
    }

    private fun setupViews() {
        levelAdapter.onClickRootView = { level, view ->
            playGame(level = level, view = view)
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
            }
        )
        LUIUtil.setSafeOnClickListenerElastic(
            view = ivPlay,
            runnable = {
                findNumberViewModel?.getFirstLevelOpen()
            }
        )
    }

    private fun setupViewModels() {
        findNumberViewModel = getViewModel(FindNumberViewModel::class.java)
        findNumberViewModel?.let { vm ->

            vm.listLevelActionLiveData.observe(this) { actionData ->
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                }

                if (isDoing == false && actionData.isSuccess == true) {
                    actionData.data?.let { listLevel ->
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
            }

            vm.firstLevelOpenActionLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                }

                if (isDoing == false && actionData.isSuccess == true) {
                    actionData.data?.let { firstLevelOpen ->
                        playGame(level = firstLevelOpen, view = ivPlay)
                    }
                }
            })
        }
    }

    private fun playGame(level: Level, view: View) {
//        logD("playGame level " + BaseApplication.gson.toJson(level))

//        val intent = Intent(this, SinglePlayActivity::class.java).apply {
//            putExtra(SinglePlayActivity.KEY_LEVEL, level)
//        }
//        startActivity(intent)
//        LActivityUtil.tranIn(this)

        val intent = Intent(this, SinglePlayActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(SinglePlayActivity.KEY_LEVEL, level)
        val builder = CircularReveal.Builder(
            this,
            view,
            intent,
            1000
        ).apply {
            revealColor = ContextCompat.getColor(
                this@SingleLevelActivity,
                R.color.orange
            )
        }
        CircularReveal.presentActivity(builder)
    }
}
