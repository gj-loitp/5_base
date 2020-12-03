package com.game.findnumber

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.*
import com.daimajia.androidanimations.library.Techniques
import kotlinx.android.synthetic.main.l_activity_find_number_menu.*

@LogTag("MenuActivity")
@IsFullScreen(true)

//TODO loitpp sound

class MenuActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupViews()
    }

    private fun setupViews() {
        LUIUtil.setDelay(100) {
            ivTitle?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = ivTitle,
                    duration = 1000,
                    techniques = Techniques.ZoomInDown
            )

            btSinglePlayer?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = btSinglePlayer,
                    duration = 1000,
                    techniques = Techniques.SlideInLeft
            )

            btTwoPlayers?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = btTwoPlayers,
                    duration = 1000,
                    techniques = Techniques.SlideInRight
            )

            ivBack?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = ivBack,
                    duration = 1000,
                    techniques = Techniques.ZoomInUp
            )

            ivMore?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = ivMore,
                    duration = 1000,
                    techniques = Techniques.ZoomInUp
            )
        }
        LUIUtil.setSafeOnClickListenerElastic(
                view = ivBack,
                runnable = {
                    onBackPressed()
                })
        LUIUtil.setSafeOnClickListenerElastic(
                view = ivMore,
                runnable = {
                    LSocialUtil.moreApp(activity = this)
                })
        LUIUtil.setSafeOnClickListenerElastic(
                view = btSinglePlayer,
                runnable = {
                    val intent = Intent(this, SingleLevelActivity::class.java)
                    startActivity(intent)
                    LActivityUtil.tranIn(this)
                })
        LUIUtil.setSafeOnClickListenerElastic(
                view = btTwoPlayers,
                runnable = {
                    //TODO 2 players
                })
    }
}
