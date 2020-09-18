package vn.loitp.app.activity.customviews.imageview.bigimageview

import android.net.Uri
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.GlideImageViewFactory
import kotlinx.android.synthetic.main.activity_imageview_big.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants
import java.io.File

//https://github.com/Piasy/BigImageViewer

@LayoutId(R.layout.activity_imageview_big)
@LogTag("BigImageViewActivity")
class BigImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        indicatorView.hide()
        bigImageView.setImageViewFactory(GlideImageViewFactory())
        bigImageView.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(imageType: Int, image: File) {}

            override fun onCacheMiss(imageType: Int, image: File) {}

            override fun onStart() {
                indicatorView.smoothToShow()
            }

            override fun onProgress(progress: Int) {
                logD("onProgress $progress")
            }

            override fun onFinish() {}

            override fun onSuccess(image: File) {
                logD("onSuccess")
                val ssiv = bigImageView.ssiv
                ssiv?.isZoomEnabled = true
                indicatorView.smoothToHide()
            }

            override fun onFail(error: Exception) {}
        })

        bt0.setOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LARGE_LAND_S), Uri.parse(Constants.URL_IMG_LARGE_LAND_O))
        }
        bt1.setOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S), Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O))
        }
        bt2.setOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LONG))
        }
        bt3.setOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }
}
