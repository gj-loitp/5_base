package com.loitp.game.findNumber.ui

import android.os.Bundle
import com.loitp.R
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.ext.*
import com.loitp.databinding.LAFindNumberSinglePlayBinding
import com.loitp.game.findNumber.model.Level
import com.tombayley.activitycircularreveal.CircularReveal

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("SinglePlayActivity")
@IsFullScreen(true)
@IsSwipeActivity(true)
class SinglePlayActivity : BaseActivityFont() {
    private lateinit var binding: LAFindNumberSinglePlayBinding

    companion object {
        const val KEY_LEVEL = "KEY_LEVEL"
    }

    private var activityCircularReveal: CircularReveal? = null
    private var frmFindNumberPlay: FrmFindNumberPlay? = null
    private var currentLevel: Level? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LAFindNumberSinglePlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.toggleFullscreen(isFullScreen = true)
        activityCircularReveal = CircularReveal(binding.rootView)
        activityCircularReveal?.onActivityCreate(intent)
        setupData()
        setupViews()
        setupViewModels()
    }

    override fun onBaseBackPressed() {
        super.onBaseBackPressed()//correct
        activityCircularReveal?.unRevealActivity(this)
    }

    private fun setupViews() {
        binding.ivBackground.loadGlide(any = currentLevel?.bkg)
        frmFindNumberPlay?.let {
            this.replaceFragment(
                containerFrameLayoutIdRes = R.id.layoutContainer,
                fragment = it,
                isAddToBackStack = false
            )
        }
        binding.ivBack.setSafeOnClickListener {
            // TODO popup exit
        }
    }

    private fun setupViewModels() {
        // do sth
    }

    private fun setupData() {
        val level = intent?.extras?.getSerializableCompat(KEY_LEVEL, Level::class.java)
        if (level == null) {
            showShortInformation(getString(R.string.err_unknown_en))
            return
        }
        currentLevel = level
        logD("setupData currentLevel " + BaseApplication.gson.toJson(currentLevel))
        currentLevel?.let {
            frmFindNumberPlay = FrmFindNumberPlay(level = it)
        }
    }
}
