package com.game.findnumber.ui

import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import com.game.findnumber.model.Level
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_find_number_single_play.*

@LogTag("SinglePlayActivity")
@IsFullScreen(true)
class SinglePlayActivity : BaseFontActivity() {

    companion object {
        const val KEY_LEVEL = "KEY_LEVEL"
    }

    private var frmFindNumberPlay: FrmFindNumberPlay? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_single_play
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupData()
        setupViews()
        setupViewModels()
    }

    private fun setupViews() {

        //TODO loitpp custom background depend on level
        LImageUtil.load(context = this, any = R.drawable.bkg_2, imageView = ivBackground)
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
        val currentLevel = intent.getSerializableExtra(KEY_LEVEL)
        if (currentLevel == null || currentLevel !is Level) {
            showShortInformation(getString(R.string.err_unknow_en))
            return
        }
        logD("setupData currentLevel " + BaseApplication.gson.toJson(currentLevel))
        frmFindNumberPlay = FrmFindNumberPlay(level = currentLevel)
    }
}
