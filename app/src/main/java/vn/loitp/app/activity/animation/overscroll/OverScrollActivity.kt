package vn.loitp.app.activity.animation.overscroll

import android.os.Bundle
import android.widget.TextView

import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil

import loitp.basemaster.R

class OverScrollActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tv = findViewById(R.id.tv) as TextView
        tv.text = LStoreUtil.readTxtFromRawFolder(activity, R.raw.overscroll)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_over_scroll
    }
}
