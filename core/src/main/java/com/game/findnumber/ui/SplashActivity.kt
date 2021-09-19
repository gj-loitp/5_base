package com.game.findnumber.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.*
import com.daimajia.androidanimations.library.Techniques
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.l_activity_find_number_splash.*

@LogTag("SplashActivity")
@IsFullScreen(true)
//TODO loitpp add sound exf later
class SplashActivity : BaseFontActivity() {

    companion object {
        const val REQUEST_CODE = 69
    }

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
                            val builder = CircularReveal.Builder(
                                    this,
                                    progressBar,
                                    intent,
                                    1000
                            ).apply {
                                revealColor = ContextCompat.getColor(
                                        this@SplashActivity,
                                        R.color.orange
                                )
                                requestCode = REQUEST_CODE
                            }
                            CircularReveal.presentActivity(builder)
                        }
                    }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            onBackPressed()
        }
    }

}
