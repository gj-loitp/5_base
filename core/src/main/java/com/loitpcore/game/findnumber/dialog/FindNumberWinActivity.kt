package com.loitpcore.game.findnumber.dialog

import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.loitpcore.R
import com.loitpcore.animation.morphtransitions.FabTransform
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsSwipeActivity
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.l_dialog_find_number_win.*

@LogTag("DialogActivity")
@IsFullScreen(true)
@IsSwipeActivity(true)
class FindNumberWinActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_dialog_find_number_win
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

    override fun onBackPressed() {
        exit()
    }

    private fun exit() {
        ActivityCompat.finishAfterTransition(this@FindNumberWinActivity)
    }
}
