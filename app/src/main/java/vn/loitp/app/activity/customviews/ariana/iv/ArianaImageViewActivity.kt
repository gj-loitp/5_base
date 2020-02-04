package vn.loitp.app.activity.customviews.ariana.iv

import android.os.Bundle
import android.widget.ImageView
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.views.ariana.Ariana
import com.views.ariana.GradientAngle
import vn.loitp.app.R

//https://github.com/akshay2211/Ariana?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6235
class ArianaImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val iv = findViewById<ImageView>(R.id.iv)

        val drawable = Ariana.drawable(LStoreUtil.colors, GradientAngle.LEFT_BOTTOM_TO_RIGHT_TOP)
        iv.background = drawable
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_ariana_iv
    }
}
