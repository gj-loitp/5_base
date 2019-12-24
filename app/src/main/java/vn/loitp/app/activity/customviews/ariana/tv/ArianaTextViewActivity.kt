package vn.loitp.app.activity.customviews.ariana.tv

import android.os.Bundle
import android.widget.TextView

import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.views.ariana.Ariana
import com.views.ariana.GradientAngle

import loitp.basemaster.R

//https://github.com/akshay2211/Ariana?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6235
class ArianaTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tv = findViewById<TextView>(R.id.tv)
        Ariana.setGradient(tv, LStoreUtil.colors, GradientAngle.LEFT_BOTTOM_TO_RIGHT_TOP)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_ariana_tv
    }
}
