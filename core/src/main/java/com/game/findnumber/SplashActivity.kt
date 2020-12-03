package com.game.findnumber

import android.content.Intent
import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.core.utilities.LValidateUtil

@LogTag("SplashActivity")
@IsFullScreen(true)
class SplashActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupViews()
        goToHome()
    }

    private fun setupViews() {
        LValidateUtil.isValidPackageName()
    }

    private fun goToHome() {
        LUIUtil.setDelay(mls = 1000, runnable = {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
            finish()
        })
    }

}
