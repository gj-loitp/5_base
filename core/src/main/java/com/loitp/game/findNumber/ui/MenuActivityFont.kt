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
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
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
class MenuActivityFont : BaseActivityFont() {

    private var activityCircularReveal: CircularReveal? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_a_find_number_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
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
        LUIUtil.setSafeOnClickListenerElastic(
            view = ivBack,
            runnable = {
                onBaseBackPressed()
            }
        )
        LUIUtil.setSafeOnClickListenerElastic(
            view = ivMore,
            runnable = {
                LSocialUtil.moreApp(activity = this)
            }
        )
        LUIUtil.setSafeOnClickListenerElastic(
            view = btSinglePlayer,
            runnable = {
                val intent = Intent(this, SingleLevelActivityFont::class.java)
                val builder = CircularReveal.Builder(
                    this,
                    btSinglePlayer,
                    intent,
                    1000
                ).apply {
                    revealColor = ContextCompat.getColor(
                        this@MenuActivityFont,
                        R.color.orange
                    )
                    requestCode = SplashActivityFont.REQUEST_CODE
                }
                CircularReveal.presentActivity(builder)
            }
        )
        LUIUtil.setSafeOnClickListenerElastic(
            view = btTwoPlayers,
            runnable = {
                // TODO 2 players
            }
        )
    }
}
