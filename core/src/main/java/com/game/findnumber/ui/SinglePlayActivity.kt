package com.game.findnumber.ui

import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil

@LogTag("SinglePlayActivity")
@IsFullScreen(true)
class SinglePlayActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_single_play
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupViews()
        setupViewModels()

    }

    private fun setupViews() {

    }

    private fun setupViewModels() {

    }
}
