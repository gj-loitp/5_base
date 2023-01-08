package vn.loitp.a.cv.iv.bigIv

import android.net.Uri
import android.os.Bundle
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.GlideImageViewFactory
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.hideProgress
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showProgress
import kotlinx.android.synthetic.main.a_big_iv.*
import vn.loitp.R
import vn.loitp.common.Constants
import java.io.File

// https://github.com/Piasy/BigImageViewer
@LogTag("BigImageViewActivity")
@IsFullScreen(false)
class BigIvActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_big_iv
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = BigIvActivityFont::class.java.simpleName
        }

        progressBar.hideProgress()
        bigImageView.setImageViewFactory(GlideImageViewFactory())
        bigImageView.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(imageType: Int, image: File) {}

            override fun onCacheMiss(imageType: Int, image: File) {}

            override fun onStart() {
                progressBar.showProgress()
            }

            override fun onProgress(progress: Int) {
                logD("onProgress $progress")
            }

            override fun onFinish() {}

            override fun onSuccess(image: File) {
                logD("onSuccess")
                val ssiv = bigImageView.ssiv
                ssiv?.isZoomEnabled = true
                progressBar.hideProgress()
            }

            override fun onFail(error: Exception) {}
        })

        bt0.setSafeOnClickListener {
            bigImageView.showImage(
                Uri.parse(Constants.URL_IMG_LARGE_LAND_S),
                Uri.parse(Constants.URL_IMG_LARGE_LAND_O)
            )
        }
        bt1.setSafeOnClickListener {
            bigImageView.showImage(
                Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S),
                Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O)
            )
        }
        bt2.setSafeOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_LONG))
        }
        bt3.setSafeOnClickListener {
            bigImageView.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        }
    }
}
