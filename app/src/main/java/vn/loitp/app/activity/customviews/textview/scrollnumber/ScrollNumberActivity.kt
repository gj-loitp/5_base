package vn.loitp.app.activity.customviews.textview.scrollnumber

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.views.textview.scrollnumber.MultiScrollNumber

import vn.loitp.app.R

class ScrollNumberActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scrollNumber = findViewById<MultiScrollNumber>(R.id.scroll_number)

        scrollNumber.setTextColors(intArrayOf(R.color.red50, R.color.orange, R.color.blue, R.color.green, R.color.purple))
        //scrollNumber.setTextSize(64);

        //scrollNumber.setNumber(64, 2048);
        //scrollNumber.setInterpolator(new DecelerateInterpolator());


        scrollNumber.setScrollVelocity(20)
        scrollNumber.setNumber(20.48)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scroll_number
    }

}
