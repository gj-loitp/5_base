package com.loitp.game.findNumber.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.loitp.R
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.hideProgress
import com.loitp.core.ext.play
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showProgress
import com.loitp.core.ext.toggleFullscreen
import com.loitp.databinding.LAFindNumberSingleLevelBinding
import com.loitp.game.findNumber.adt.LevelAdapter
import com.loitp.game.findNumber.model.Level
import com.loitp.game.findNumber.vm.FindNumberViewModel
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.l_a_find_number_single_level.ivBack
import kotlinx.android.synthetic.main.l_a_find_number_single_level.ivPlay
import kotlinx.android.synthetic.main.l_a_find_number_single_level.ivSpiral
import kotlinx.android.synthetic.main.l_a_find_number_single_level.layoutLevel
import kotlinx.android.synthetic.main.l_a_find_number_single_level.progressBar
import kotlinx.android.synthetic.main.l_a_find_number_single_level.rootView
import kotlinx.android.synthetic.main.l_a_find_number_single_level.rvLevel
import kotlinx.android.synthetic.main.l_a_find_number_single_level.tvLevels

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("SingleLevelActivity")
@IsFullScreen(true)
@IsSwipeActivity(true)
class SingleLevelActivity : BaseActivityFont() {
    private var activityCircularReveal: CircularReveal? = null
    private var levelAdapter = LevelAdapter()
    private var findNumberViewModel: FindNumberViewModel? = null
    private lateinit var binding: LAFindNumberSingleLevelBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LAFindNumberSingleLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.toggleFullscreen(isFullScreen = true)
        activityCircularReveal = CircularReveal(rootView)
        activityCircularReveal?.onActivityCreate(intent)

        setupViews()
        setupViewModels()

        findNumberViewModel?.getListLevelSingle()
    }

    override fun onBaseBackPressed() {
        super.onBaseBackPressed()//correct
        activityCircularReveal?.unRevealActivity(this)
    }

    private fun setupViews() {
        levelAdapter.onClickRootView = { level, view ->
            playGame(level = level, view = view)
        }
        rvLevel.layoutManager = GridLayoutManager(this, 4)
        rvLevel.adapter = levelAdapter

        setDelay(100) {
            tvLevels?.visibility = View.VISIBLE
            tvLevels?.play(
                duration = 1000,
                techniques = Techniques.ZoomInDown
            )

            ivBack?.visibility = View.VISIBLE
            ivBack?.play(
                duration = 1000,
                techniques = Techniques.ZoomInUp
            )

            ivPlay?.visibility = View.VISIBLE
            ivPlay?.play(
                duration = 1000,
                techniques = Techniques.ZoomInUp
            )

            ivSpiral?.visibility = View.VISIBLE
            ivSpiral?.play(
                duration = 5000,
                techniques = Techniques.RotateIn,
                repeatCount = -1
            )
        }
        tvLevels.setSafeOnClickListenerElastic(
        )
        ivBack.setSafeOnClickListenerElastic(
            runnable = {
                onBaseBackPressed()
            }
        )
        ivPlay.setSafeOnClickListenerElastic(
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
                    progressBar.showProgress()
                } else {
                    progressBar.hideProgress()
                }

                if (isDoing == false && actionData.isSuccess == true) {
                    actionData.data?.let { listLevel ->
                        levelAdapter.setListLevel(listLevel = listLevel)

                        setDelay(mls = 100, runnable = {
                            layoutLevel?.visibility = View.VISIBLE
                            layoutLevel?.play(
                                duration = 1000,
                                techniques = Techniques.FadeInUp
                            )
                        })
                    }
                }
            }

            vm.firstLevelOpenActionLiveData.observe(this) { actionData ->
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    progressBar.showProgress()
                } else {
                    progressBar.hideProgress()
                }

                if (isDoing == false && actionData.isSuccess == true) {
                    actionData.data?.let { firstLevelOpen ->
                        playGame(level = firstLevelOpen, view = ivPlay)
                    }
                }
            }
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
