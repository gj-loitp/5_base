package vn.loitp.a.cv.fancyShowcase

import com.loitp.core.base.BaseFontActivity
import me.toptas.fancyshowcase.FancyShowCaseView

abstract class BaseActivityFancyShowcase : BaseFontActivity() {

//    override fun onBackPressed() {
//        if (FancyShowCaseView.isVisible(this)) {
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
