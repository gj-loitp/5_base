package vn.loitp.app.activity.customviews.imageview.bigimageview

import android.net.Uri
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.GlideImageViewFactory
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_imageview_big.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants
import java.io.File

//https://github.com/Piasy/BigImageViewer

@LayoutId(R.layout.activity_imageview_big)
@LogTag("BigImageViewActivity")
@IsFullScreen(false)
class BigImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews(){
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

        bt0.setSafeOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LARGE_LAND_S), Uri.parse(Constants.URL_IMG_LARGE_LAND_O))
        }
        bt1.setSafeOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S), Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O))
        }
        bt2.setSafeOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LONG))
        }
        bt3.setSafeOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        }
    }
}
