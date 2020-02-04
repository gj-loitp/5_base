package vn.loitp.app.activity.customviews.textview.scrollnumber

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.views.textview.scrollnumber.MultiScrollNumber

import vn.loitp.app.R

class ScrollNumberActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scrollNumber = findViewById<MultiScrollNumber>(R.id.scroll_number)

        scrollNumber.setTextColors(intArrayOf(R.color.red01, R.color.orange01, R.color.blue01, R.color.green01, R.color.purple01))
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
