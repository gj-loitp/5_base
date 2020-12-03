package com.game.findnumber

import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.core.utilities.LValidateUtil

@LogTag("SplashActivity")
@IsFullScreen(false)
class SplashActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        goToHome()
    }

    private fun setupViews() {
        LValidateUtil.isValidPackageName()
    }

    private fun goToHome() {
        LUIUtil.setDelay(mls = 1500, runnable = {
//            val intent = Intent(this, ComicLoginActivity::class.java)
//            startActivity(intent)
//            LActivityUtil.tranIn(this)
//            finish()
        })
    }

}
