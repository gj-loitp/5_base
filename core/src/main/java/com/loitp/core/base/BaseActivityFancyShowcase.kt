package com.loitp.core.base

import me.toptas.fancyshowcase.FancyShowCaseView

abstract class BaseActivityFancyShowcase : BaseFontActivity() {

//    override fun onBackPressed() {
//        if (Fa¬ncyShowCaseView.isVisible(this)) {
//            FancyShowCaseView.hideCurrent(this)
//        } else {
//            super.onBackPressed()
//        }
//    }

    override fun onBaseBackPressed() {
        if (FancyShowCaseView.isVisible(this)) {
            FancyShowCaseView.hideCurrent(this)
        } else {
            super.onBaseBackPressed()
        }
    }
}
