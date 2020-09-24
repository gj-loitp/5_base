package com.core.helper.girl.ui

import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity

@LogTag("GirlActivity")
@IsFullScreen(false)
class GirlDetailActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_girl_detail)

        setupViews()
    }


    private fun setupViews() {

    }
}
