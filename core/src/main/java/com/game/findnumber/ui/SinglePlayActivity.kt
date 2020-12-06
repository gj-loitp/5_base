package com.game.findnumber.ui

import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.game.findnumber.model.Level

@LogTag("SinglePlayActivity")
@IsFullScreen(true)
class SinglePlayActivity : BaseFontActivity() {

    companion object {
        const val KEY_LEVEL = "KEY_LEVEL"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_single_play
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupViews()
        setupViewModels()
        setupData()
    }

    private fun setupViews() {

    }

    private fun setupViewModels() {

    }

    private fun setupData() {
        val currentLevel = intent.getSerializableExtra(KEY_LEVEL)
        if (currentLevel == null || currentLevel !is Level) {
            showShortInformation(getString(R.string.err_unknow_en))
            return
        }
        logD("setupData currentLevel " + BaseApplication.gson.toJson(currentLevel))
    }
}
