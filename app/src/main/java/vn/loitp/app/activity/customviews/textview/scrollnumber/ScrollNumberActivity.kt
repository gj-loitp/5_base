package vn.loitp.app.activity.customviews.textview.scrollnumber

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_text_view_scroll_number.*
import vn.loitp.app.R

@LogTag("ScrollNumberActivity")
@IsFullScreen(false)
class ScrollNumberActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_scroll_number
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        multiScrollNumber.setTextColors(intArrayOf(R.color.red50, R.color.orange, R.color.blue, R.color.green, R.color.purple))
        //scrollNumber.setTextSize(64);

        //scrollNumber.setNumber(64, 2048);
        //scrollNumber.setInterpolator(new DecelerateInterpolator());


        multiScrollNumber.setScrollVelocity(20)
        multiScrollNumber.setNumber(20.48)
    }

}
