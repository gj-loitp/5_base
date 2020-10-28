package vn.loitp.app.activity.customviews.imageview.circleimageview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_imageview_circle.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LayoutId(R.layout.activity_imageview_circle)
@LogTag("CircleImageViewActivity")
@IsFullScreen(false)
class CircleImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews(){
        val resPlaceHolder = R.color.red
        LImageUtil.loadRound(url = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg", imageView = imageView, roundingRadius = 45, resPlaceHolder = resPlaceHolder)
        LImageUtil.loadCircle(url = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg", imageView = iv1)
        LImageUtil.loadCircle(url = Constants.URL_IMG_LARGE, imageView = iv2, resPlaceHolder = R.color.red, resError = R.drawable.l_error_404)
        LImageUtil.load(context = this, any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg", imageView = iv)
    }
}
