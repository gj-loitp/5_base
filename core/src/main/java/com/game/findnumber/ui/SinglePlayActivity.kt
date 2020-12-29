package com.game.findnumber.ui

import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import com.game.findnumber.model.Level
import com.tombayley.activitycircularreveal.CircularReveal
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_find_number_single_play.*

@LogTag("SinglePlayActivity")
@IsFullScreen(true)
@IsSwipeActivity(true)
class SinglePlayActivity : BaseFontActivity() {

    companion object {
        const val KEY_LEVEL = "KEY_LEVEL"
    }

    private var activityCircularReveal: CircularReveal? = null
    private var frmFindNumberPlay: FrmFindNumberPlay? = null
    private var currentLevel: Level? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_single_play
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        activityCircularReveal = CircularReveal(rootView)
        activityCircularReveal?.onActivityCreate(intent)
        setupData()
        setupViews()
        setupViewModels()
    }

    override fun onBackPressed() {
        activityCircularReveal?.unRevealActivity(this)
    }

    private fun setupViews() {
        LImageUtil.load(context = this, any = currentLevel?.bkg, imageView = ivBackground)
        frmFindNumberPlay?.let {
            LScreenUtil.replaceFragment(
                    activity = this,
                    containerFrameLayoutIdRes = R.id.layoutContainer,
                    fragment = it,
                    isAddToBackStack = false
            )
        }
        ivBack.setSafeOnClickListener {
            //TODO loitpp popup exit
        }
    }

    private fun setupViewModels() {
        //do sth
    }

    private fun setupData() {
        val level = intent.getSerializableExtra(KEY_LEVEL)
        if (level == null || level !is Level) {
            showShortInformation(getString(R.string.err_unknow_en))
            return
        }
        currentLevel = level
        logD("setupData currentLevel " + BaseApplication.gson.toJson(currentLevel))
        currentLevel?.let {
            frmFindNumberPlay = FrmFindNumberPlay(level = it)
        }
    }
}
