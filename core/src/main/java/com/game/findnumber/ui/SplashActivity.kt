package com.game.findnumber.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.*
import com.daimajia.androidanimations.library.Techniques
import kotlinx.android.synthetic.main.l_activity_find_number_splash.*

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
        LUIUtil.setDelay(100) {
            ivFindTheNumber?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = ivFindTheNumber,
                    duration = 500,
                    techniques = Techniques.ZoomIn,
                    onEnd = {
                        if (!this.isFinishing) {
                            val intent = Intent(this, MenuActivity::class.java)
                            startActivity(intent)
                            LActivityUtil.tranIn(this)
                            finish()
                        }
                    }
            )
        }
    }

}
