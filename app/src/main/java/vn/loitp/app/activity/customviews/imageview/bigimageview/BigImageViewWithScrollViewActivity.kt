package vn.loitp.app.activity.customviews.imageview.bigimageview

import android.net.Uri
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.github.piasy.biv.view.GlideImageViewFactory
import kotlinx.android.synthetic.main.activity_imageview_big_with_scroll_view.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

//https://github.com/Piasy/BigImageViewer
@LayoutId(R.layout.activity_imageview_big_with_scroll_view)
@LogTag("BigImageViewWithScrollViewActivity")
@IsFullScreen(false)
class BigImageViewWithScrollViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        biv0.setImageViewFactory(GlideImageViewFactory())
        biv1.setImageViewFactory(GlideImageViewFactory())
        biv2.setImageViewFactory(GlideImageViewFactory())
        biv3.setImageViewFactory(GlideImageViewFactory())

        biv0.showImage(Uri.parse(Constants.URL_IMG_LARGE_LAND_S), Uri.parse(Constants.URL_IMG_LARGE_LAND_O))
        biv1.showImage(Uri.parse(Constants.URL_IMG_LONG))
        biv2.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        biv3.showImage(Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S), Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O))
    }
}
