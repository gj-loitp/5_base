package com.game.findnumber.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.l_activity_find_number_menu.*

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

    override fun onBackPressed() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        activityCircularReveal?.unRevealActivity(this)
    }

    private fun setupViews() {
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
