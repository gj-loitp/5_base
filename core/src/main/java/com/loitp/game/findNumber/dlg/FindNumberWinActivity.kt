package com.loitp.game.findNumber.dlg

import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.loitp.R
import com.loitp.anim.morphTransitions.FabTransform
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.l_d_find_number_win.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("FindNumberWinActivity")
@IsFullScreen(true)
@IsSwipeActivity(true)
class FindNumberWinActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_d_find_number_win
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupViews()
    }

    private fun setupViews() {
        rootView.setOnClickListener {
//            exit()
            // do nothing
        }
        FabTransform.setup(this, container)
    }

//    override fun onBackPressed() {
//        exit()
//    }

    override fun onBaseBackPressed() {
        super.onBaseBackPressed()//correct
        exit()
    }

    private fun exit() {
        ActivityCompat.finishAfterTransition(this@FindNumberWinActivity)
    }
}
