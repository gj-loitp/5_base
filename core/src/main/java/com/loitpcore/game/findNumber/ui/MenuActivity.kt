package com.loitpcore.game.findNumber.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.loitpcore.R
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsSwipeActivity
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.l_activity_find_number_menu.*

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
class MenuActivity : BaseFontActivity() {

    private var activityCircularReveal: CircularReveal? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        activityCircularReveal = CircularReveal(rootView)
        activityCircularReveal?.onActivityCreate(intent)

        setupViews()
    }

//    override fun onBackPressed() {
//        val intent = Intent()
//        setResult(Activity.RESULT_OK, intent)
//        activityCircularReveal?.unRevealActivity(this)
//    }

    override fun onBaseBackPressed() {
        super.onBaseBackPressed()
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        activityCircularReveal?.unRevealActivity(this)
    }

    private fun setupViews() {
        LUIUtil.setSafeOnClickListenerElastic(
            view = ivBack,
            runnable = {
//                onBackPressed()
                finish()
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
        LUIUtil.setSafeOnClickListenerElastic(
            view = btTwoPlayers,
            runnable = {
                // TODO 2 players
            }
        )
    }
}
