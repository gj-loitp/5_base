package vn.loitp.a.cv.fancyShowcase

import android.os.Bundle
import android.view.View
import com.loitp.core.base.BaseActivityFancyShowcaseFont
import kotlinx.android.synthetic.main.a_fancy_showcase_second.*
import me.toptas.fancyshowcase.FancyShowCaseView
import vn.loitp.R

class SecondActivityFancyShowcaseFont : BaseActivityFancyShowcaseFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_fancy_showcase_second
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        focusOnButton()

        button1.setOnClickListener {
            focusOnButton()
        }

        button2.setOnClickListener {
            if (toolbar.visibility == View.VISIBLE) {
                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }
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
