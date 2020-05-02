package vn.loitp.app.activity.customviews.imageview.circleimageview

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_imageview_circle.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

class CircleImageViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_circle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val resPlaceHolder = R.color.colorPrimary
        LImageUtil.loadRound(url = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg", imageView = iv, roundingRadius = 45, resPlaceHolder = resPlaceHolder)
        LImageUtil.loadCircle(url = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg", imageView = iv1)
        LImageUtil.loadCircle(url = Constants.URL_IMG_LARGE, imageView = iv2, resPlaceHolder = R.color.red, resError = R.drawable.l_error_404)
    }
}
