package vn.loitp.up.a.cv.iv.bigIv

import android.net.Uri
import android.os.Bundle
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.GlideImageViewFactory
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.hideProgress
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showProgress
import vn.loitp.R
import vn.loitp.databinding.ABigIvBinding
import vn.loitp.up.common.Constants
import java.io.File

// https://github.com/Piasy/BigImageViewer
@LogTag("BigImageViewActivity")
@IsFullScreen(false)
class BigIvActivity : BaseActivityFont() {
    private lateinit var binding: ABigIvBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ABigIvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = BigIvActivity::class.java.simpleName
        }

        binding.progressBar.hideProgress()
        binding.bigImageView.setImageViewFactory(GlideImageViewFactory())
        binding.bigImageView.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(imageType: Int, image: File) {}

            override fun onCacheMiss(imageType: Int, image: File) {}

            override fun onStart() {
                binding.progressBar.showProgress()
            }

            override fun onProgress(progress: Int) {
                logD("onProgress $progress")
            }

            override fun onFinish() {}

            override fun onSuccess(image: File) {
                logD("onSuccess")
                val ssiv = binding.bigImageView.ssiv
                ssiv?.isZoomEnabled = true
                binding.progressBar.hideProgress()
            }

            override fun onFail(error: Exception) {}
        })

        binding.bt0.setSafeOnClickListener {
            binding.bigImageView.showImage(
                Uri.parse(Constants.URL_IMG_LARGE_LAND_S),
                Uri.parse(Constants.URL_IMG_LARGE_LAND_O)
            )
        }
        binding.bt1.setSafeOnClickListener {
            binding.bigImageView.showImage(
                Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S),
                Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O)
            )
        }
        binding.bt2.setSafeOnClickListener {
            binding.bigImageView.showImage(Uri.parse(Constants.URL_IMG_LONG))
        }
        binding.bt3.setSafeOnClickListener {
            binding.bigImageView.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        }
    }
}
