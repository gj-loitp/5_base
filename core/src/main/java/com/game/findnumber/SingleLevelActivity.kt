package com.game.findnumber

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import kotlinx.android.synthetic.main.l_activity_find_number_menu.*
import kotlinx.android.synthetic.main.l_activity_find_number_menu.ivTitle
import kotlinx.android.synthetic.main.l_activity_find_number_single_level.*

@LogTag("SingleLevelActivity")
@IsFullScreen(true)
class SingleLevelActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_single_level
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupViews()
    }

    private fun setupViews() {
        LUIUtil.setDelay(100) {
            tvLevels?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = tvLevels,
                    duration = 500,
                    techniques = Techniques.ZoomInDown
            )
        }
        LUIUtil.setSafeOnClickListenerElastic(
                view = tvLevels
        )
//        LUIUtil.setSafeOnClickListenerElastic(
//                view = ivMore,
//                runnable = {
//                    LSocialUtil.moreApp(activity = this)
//                })
    }
}
