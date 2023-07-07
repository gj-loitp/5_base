package com.loitp.game.findNumber.dlg

import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.loitp.R
import com.loitp.anim.morphTransitions.FabTransform
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.toggleFullscreen
import com.loitp.databinding.LATttComicLoginBinding
import com.loitp.databinding.LDFindNumberWinBinding

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
    private lateinit var binding: LDFindNumberWinBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LDFindNumberWinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.toggleFullscreen(isFullScreen = true)
        setupViews()
    }

    private fun setupViews() {
        binding.rootView.setOnClickListener {
//            exit()
            // do nothing
        }
        FabTransform.setup(this, binding.container)
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
