package vn.loitp.app.activity.customviews.imageview.bigimageview

import android.net.Uri
import android.os.Bundle

import com.core.base.BaseFontActivity
import com.github.piasy.biv.view.BigImageView
import com.github.piasy.biv.view.GlideImageViewFactory

import vn.loitp.app.R
import vn.loitp.app.common.Constants

//https://github.com/Piasy/BigImageViewer
class BigImageViewWithScrollViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biv0 = findViewById<BigImageView>(R.id.biv_0)
        biv0.setImageViewFactory(GlideImageViewFactory())
        val biv1 = findViewById<BigImageView>(R.id.biv_1)
        biv1.setImageViewFactory(GlideImageViewFactory())
        val biv2 = findViewById<BigImageView>(R.id.biv_2)
        biv2.setImageViewFactory(GlideImageViewFactory())
        val biv3 = findViewById<BigImageView>(R.id.biv_3)
        biv3.setImageViewFactory(GlideImageViewFactory())

        biv0.showImage(Uri.parse(Constants.URL_IMG_LARGE_LAND_S), Uri.parse(Constants.URL_IMG_LARGE_LAND_O))
        biv1.showImage(Uri.parse(Constants.URL_IMG_LONG))
        biv2.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        biv3.showImage(Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S), Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O))
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_big_imageview_with_scroll_view
    }
}
