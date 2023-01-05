package vn.loitp.a.cv.fancyShowcase

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFancyShowcaseFont
import kotlinx.android.synthetic.main.a_fancy_showcase_second.*
import me.toptas.fancyshowcase.FancyShowCaseView
import vn.loitp.R

@LogTag("SecondActivityFancyShowcaseFont")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SecondActivityFancyShowcaseFont : BaseActivityFancyShowcaseFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_fancy_showcase_second
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        focusOnButton()
        button1.setOnClickListener {
            focusOnButton()
        }
    }

    private fun focusOnButton() {
        FancyShowCaseView.Builder(this@SecondActivityFancyShowcaseFont)
            .focusOn(button1)
            .title("Focus a view")
            .fitSystemWindows(true)
            .build()
            .show()
    }

}
