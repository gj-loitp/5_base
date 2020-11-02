package vn.loitp.app.activity.customviews.imageview.strectchyimageview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_imageview_strectchy.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LogTag("StrectchyImageViewActivity")
@IsFullScreen(false)
class StrectchyImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_strectchy
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LImageUtil.load(context = this, any = Constants.URL_IMG_LONG, imageView = lStretchyImageView)
    }
}
