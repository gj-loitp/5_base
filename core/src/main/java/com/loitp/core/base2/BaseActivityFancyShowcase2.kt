package com.loitp.core.base2

import me.toptas.fancyshowcase.FancyShowCaseView

abstract class BaseActivityFancyShowcase2 : BaseActivityFont2() {

    override fun onBaseBackPressed() {
        if (FancyShowCaseView.isVisible(this)) {
            FancyShowCaseView.hideCurrent(this)
        } else {
            super.onBaseBackPressed()
        }
    }
}
