package com.loitp.core.base

import me.toptas.fancyshowcase.FancyShowCaseView

abstract class BaseActivityFancyShowcase : BaseActivityFont() {

    override fun onBaseBackPressed() {
        if (FancyShowCaseView.isVisible(this)) {
            FancyShowCaseView.hideCurrent(this)
        } else {
            super.onBaseBackPressed()
        }
    }
}
