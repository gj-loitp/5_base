package vn.loitp.app.activity.customviews.imageview.bigImageView

import android.net.Uri
import android.os.Bundle
import androidx.core.view.isVisible
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.GlideImageViewFactory
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LDialogUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_big_image_view.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants
import java.io.File

// https://github.com/Piasy/BigImageViewer
@LogTag("BigImageViewActivity")
@IsFullScreen(false)
class BigImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_big_image_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = BigImageViewActivity::class.java.simpleName
        }

        LDialogUtil.hideProgress(progressBar)
        bigImageView.setImageViewFactory(GlideImageViewFactory())
        bigImageView.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(imageType: Int, image: File) {}

            override fun onCacheMiss(imageType: Int, image: File) {}

            override fun onStart() {
                LDialogUtil.showProgress(progressBar)
            }

            override fun onProgress(progress: Int) {
                logD("onProgress $progress")
            }

            override fun onFinish() {}

            override fun onSuccess(image: File) {
                logD("onSuccess")
                val ssiv = bigImageView.ssiv
                ssiv?.isZoomEnabled = true
                LDialogUtil.hideProgress(progressBar)
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
