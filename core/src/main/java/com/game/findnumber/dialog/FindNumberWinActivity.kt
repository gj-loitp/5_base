package com.game.findnumber.dialog

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.R
import com.animation.morphtransitions.FabTransform
import com.animation.morphtransitions.MorphTransform
import com.annotation.IsFullScreen
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.l_dialog_find_number_win.*

@LogTag("DialogActivity")
@IsFullScreen(true)
@IsSwipeActivity(true)
class FindNumberWinActivity : BaseFontActivity() {

    companion object {
        private const val EXTRA_TYPE = "type"
        const val TYPE_FAB = 1
        const val TYPE_BUTTON = 2

        fun newIntent(context: Context, type: Int): Intent {
            val intent = Intent(context, FindNumberWinActivity::class.java)
            intent.putExtra(EXTRA_TYPE, type)
            return intent
        }
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_dialog_find_number_win
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)

        rootView.setOnClickListener {
            ActivityCompat.finishAfterTransition(this@FindNumberWinActivity)
        }
        when (intent.getIntExtra(EXTRA_TYPE, -1)) {
            TYPE_FAB -> FabTransform.setup(this, container)
            TYPE_BUTTON -> MorphTransform.setup(this, container, Color.WHITE, resources.getDimensionPixelSize(R.dimen.round_medium))
        }
    }

}
