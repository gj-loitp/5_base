package com.loitpcore.core.helper.ttt.ui.a

import android.content.Intent
import android.os.Bundle
import com.loitpcore.R
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("TTTComicLoginActivity")
@IsFullScreen(false)
class TTTComicLoginActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_ttt_comic_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        //do sth
    }

    private fun setupViewModels() {
        val intent = Intent(this, TTTComicActivity::class.java)
        startActivity(intent)
        LActivityUtil.tranIn(context = this)
        finish()//correct
    }
}
