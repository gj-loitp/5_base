package vn.loitp.app.activity.customviews.imageview.bigimageview

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.BigImageView
import com.github.piasy.biv.view.GlideImageViewFactory
import com.views.progressloadingview.avl.LAVLoadingIndicatorView
import vn.loitp.app.R
import vn.loitp.app.common.Constants
import java.io.File

//https://github.com/Piasy/BigImageViewer
class BigImageViewActivity : BaseFontActivity() {
    private lateinit var LAVLoadingIndicatorView: LAVLoadingIndicatorView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LAVLoadingIndicatorView = findViewById(R.id.avi)
        LAVLoadingIndicatorView.hide()
        val bigImageView = findViewById<BigImageView>(R.id.mBigImage)
        bigImageView.setImageViewFactory(GlideImageViewFactory())
        bigImageView.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(imageType: Int, image: File) {}

            override fun onCacheMiss(imageType: Int, image: File) {}

            override fun onStart() {
                LAVLoadingIndicatorView.smoothToShow()
            }

            override fun onProgress(progress: Int) {
                LLog.d(TAG, "onProgress $progress")
            }

            override fun onFinish() {}

            override fun onSuccess(image: File) {
                LLog.d(TAG, "onSuccess")
                val ssiv = bigImageView.ssiv
                if (ssiv != null) {
                    ssiv.isZoomEnabled = true
                }
                LAVLoadingIndicatorView.smoothToHide()
            }

            override fun onFail(error: Exception) {}
        })

        findViewById<View>(R.id.bt_0).setOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LARGE_LAND_S), Uri.parse(Constants.URL_IMG_LARGE_LAND_O))
        }
        findViewById<View>(R.id.bt_1).setOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S), Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O))
        }
        findViewById<View>(R.id.bt_2).setOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LONG))
        }
        findViewById<View>(R.id.bt_3).setOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_big_imageview
    }
}
