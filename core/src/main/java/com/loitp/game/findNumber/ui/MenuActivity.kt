package com.loitp.game.findNumber.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.loitp.R
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.moreApp
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.toggleFullscreen
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.l_a_find_number_menu.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("MenuActivity")
@IsFullScreen(true)
@IsSwipeActivity(true)
class MenuActivity : BaseActivityFont() {

    private var activityCircularReveal: CircularReveal? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_a_find_number_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.toggleFullscreen(isFullScreen = true)
        activityCircularReveal = CircularReveal(rootView)
        activityCircularReveal?.onActivityCreate(intent)

        setupViews()
    }

    override fun onBaseBackPressed() {
        super.onBaseBackPressed()//correct
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        activityCircularReveal?.unRevealActivity(this)
    }

    private fun setupViews() {
        ivBack.setSafeOnClickListenerElastic(
            runnable = {
                onBaseBackPressed()
            }
        )
        ivMore.setSafeOnClickListenerElastic(
            runnable = {
                this.moreApp()
            }
        )
        btSinglePlayer.setSafeOnClickListenerElastic(
            runnable = {
                val intent = Intent(this, SingleLevelActivity::class.java)
                val builder = CircularReveal.Builder(
                    this,
                    btSinglePlayer,
                    intent,
                    1000
                ).apply {
                    revealColor = ContextCompat.getColor(
                        this@MenuActivity,
                        R.color.orange
                    )
                    requestCode = SplashActivity.REQUEST_CODE
                }
                CircularReveal.presentActivity(builder)
            }
        )
        btTwoPlayers.setSafeOnClickListenerElastic(
            runnable = {
                // TODO 2 players
            }
        )
    }
}
