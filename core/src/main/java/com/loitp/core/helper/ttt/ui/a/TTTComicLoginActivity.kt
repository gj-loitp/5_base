package com.loitp.core.helper.ttt.ui.a

import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.tranIn
import com.loitp.databinding.LATttComicLoginBinding

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("TTTComicLoginActivity")
@IsFullScreen(false)
class TTTComicLoginActivity : BaseActivityFont() {
    private lateinit var binding: LATttComicLoginBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LATttComicLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        //do sth
    }

    private fun setupViewModels() {
        val intent = Intent(this, TTTComicActivity::class.java)
        startActivity(intent)
        this.tranIn()
        finish()//correct
    }
}
