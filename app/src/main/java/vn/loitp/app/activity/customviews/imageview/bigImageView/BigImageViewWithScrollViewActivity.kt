package vn.loitp.app.activity.customviews.imageview.bigImageView

import android.net.Uri
import android.os.Bundle
import com.github.piasy.biv.view.GlideImageViewFactory
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_big_image_view_with_scroll_view.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

// https://github.com/Piasy/BigImageViewer
@LogTag("BigImageViewWithScrollViewActivity")
@IsFullScreen(false)
class BigImageViewWithScrollViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_big_image_view_with_scroll_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        biv0.setImageViewFactory(GlideImageViewFactory())
        biv1.setImageViewFactory(GlideImageViewFactory())
        biv2.setImageViewFactory(GlideImageViewFactory())
        biv3.setImageViewFactory(GlideImageViewFactory())

        biv0.showImage(
            Uri.parse(Constants.URL_IMG_LARGE_LAND_S),
            Uri.parse(Constants.URL_IMG_LARGE_LAND_O)
        )
        biv1.showImage(Uri.parse(Constants.URL_IMG_LONG))
        biv2.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        biv3.showImage(
            Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S),
            Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O)
        )
    }
}
