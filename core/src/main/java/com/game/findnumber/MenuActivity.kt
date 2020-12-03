package com.game.findnumber

import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LSocialUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_find_number_menu.*

@LogTag("MenuActivity")
@IsFullScreen(true)
//TODO single
//TODO time mode
//TODO 2 players
class MenuActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        ivBack.setSafeOnClickListener {
            onBackPressed()
        }
        ivMore.setSafeOnClickListener {
            LSocialUtil.moreApp(activity = this)
        }
    }
}
