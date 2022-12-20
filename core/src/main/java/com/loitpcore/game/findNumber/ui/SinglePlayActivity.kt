package com.loitpcore.game.findNumber.ui

import android.os.Bundle
import com.loitpcore.R
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.game.findNumber.model.Level
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.l_activity_find_number_single_play.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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

//    override fun onBackPressed() {
//        activityCircularReveal?.unRevealActivity(this)
//    }

    override fun onBaseBackPressed() {
        super.onBaseBackPressed()//correct
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
            // TODO popup exit
        }
    }

    private fun setupViewModels() {
        // do sth
    }

    private fun setupData() {
        //TODO fix getSerializableExtra
        val level = intent.getSerializableExtra(KEY_LEVEL)
        if (level == null || level !is Level) {
            showShortInformation(getString(R.string.err_unknown_en))
            return
        }
        currentLevel = level
        logD("setupData currentLevel " + BaseApplication.gson.toJson(currentLevel))
        currentLevel?.let {
            frmFindNumberPlay = FrmFindNumberPlay(level = it)
        }
    }
}
